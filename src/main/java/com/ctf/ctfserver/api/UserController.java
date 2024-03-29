package com.ctf.ctfserver.api;


import com.ctf.ctfserver.domain.HttpResponse;
import com.ctf.ctfserver.domain.UserPrincipal;
import com.ctf.ctfserver.domain.entities.User;
import com.ctf.ctfserver.domain.models.binding.UsersEditOrDeleteBindingModel;
import com.ctf.ctfserver.domain.models.mapper.UserMapper;
import com.ctf.ctfserver.domain.models.binding.UserRegisterBindingModel;
import com.ctf.ctfserver.domain.models.response.UserResponseModel;
import com.ctf.ctfserver.domain.models.service.UserServiceModel;
import com.ctf.ctfserver.exception.ExceptionHandling;
import com.ctf.ctfserver.exception.domain.UserNotFoundException;
import com.ctf.ctfserver.exception.domain.UsernameExistsException;
import com.ctf.ctfserver.service.user.UserService;
import com.ctf.ctfserver.utility.JWTTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

import static com.ctf.ctfserver.constant.SecurityConstant.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/api", "/"}) //include "/" for the error controller to map the /error path correctly!!!
public class UserController extends ExceptionHandling {

    private final UserService userService;
    private final JWTTokenProvider jwtTokenProvider;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ResponseEntity<List<UserResponseModel>>getUsers() throws UserNotFoundException {

        List<UserResponseModel> allUsers = this.userService.getUsers().stream()
                .map(UserMapper.INSTANCE::userServiceModelToUserResponseModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(allUsers);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ResponseEntity<UserResponseModel>getUserById(@PathVariable String id) {

        UserResponseModel user = UserMapper.INSTANCE.userServiceModelToUserResponseModel(userService.getUserById(id));

        return ResponseEntity.ok().body(user);
    }


    @PostMapping("/users/delete")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ResponseEntity<HttpResponse> deleteUsers(@RequestBody final List<UsersEditOrDeleteBindingModel> users) throws UserNotFoundException, UsernameExistsException {
        List<UserServiceModel> userServiceModels = UserMapper.INSTANCE
                .ListUserDeleteBindingToListUserServiceModel(users);
        this.userService.deleteUsers(userServiceModels);

        return new ResponseEntity<>(new HttpResponse(new Date(),
                HttpStatus.OK.value(),
                HttpStatus.OK,
                HttpStatus.OK.getReasonPhrase().toUpperCase(),
                USERS_DELETED_SUCCESSFULLY.toUpperCase()), HttpStatus.OK);
    }

    @PatchMapping("/users/edit")
    @PreAuthorize("hasRole('ROLE_ROOT')")
    public ResponseEntity<HttpResponse> editUsers(@RequestBody final List<UsersEditOrDeleteBindingModel> users) throws UserNotFoundException, UsernameExistsException {
        List<UserServiceModel> userServiceModels = UserMapper.INSTANCE
                .ListUserDeleteBindingToListUserServiceModel(users);
        this.userService.editUsers(userServiceModels);

        return new ResponseEntity<>(new HttpResponse(new Date(),
                HttpStatus.OK.value(),
                HttpStatus.OK,
                HttpStatus.OK.getReasonPhrase().toUpperCase(),
                USERS_EDITED_SUCCESSFULLY.toUpperCase()), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseModel> register(@RequestBody UserRegisterBindingModel userRegisterBindingModel) throws UserNotFoundException, UsernameExistsException {

        UserServiceModel userServiceModel = UserMapper.INSTANCE
                .userServiceBindingModelToUserServiceModel(userRegisterBindingModel);
        UserResponseModel newUser = UserMapper
                .INSTANCE.userServiceModelToUserResponseModel(this.userService.register(userServiceModel));
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }



    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {

            String refreshToken = authorizationHeader.substring(TOKEN_PREFIX.length());
            String username = jwtTokenProvider.getSubject(refreshToken);
            if (jwtTokenProvider.isTokenValid(refreshToken)) {

                UserPrincipal user = new UserPrincipal(this.userService.findUserByUsername(username));
                String newAccessToken = jwtTokenProvider.generateJWTToken(user);

                response.setHeader(JWT_TOKEN_HEADER, newAccessToken);
                response.setHeader(JWT_REFRESH_TOKEN_HEADER, refreshToken);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

}

