package com.ctf.ctfserver.api;


import com.ctf.ctfserver.domain.UserPrincipal;
import com.ctf.ctfserver.domain.entities.User;
import com.ctf.ctfserver.domain.models.mapper.UserMapper;
import com.ctf.ctfserver.domain.models.binding.UserRegisterBindingModel;
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
import java.io.IOException;
import java.util.*;

import static com.ctf.ctfserver.constant.SecurityConstant.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/api", "/"}) //include "/" for the error controller to map the /error path correctly!!!
public class UserController extends ExceptionHandling {

    private final UserService userService;
    private final JWTTokenProvider jwtTokenProvider;

    @GetMapping("/users")
    public ResponseEntity<List<User>>getUsers() throws UserNotFoundException {

        return ResponseEntity.ok().body(userService.getUsers());
    }


    @PostMapping("/register")
    public ResponseEntity<UserServiceModel> register(@RequestBody UserRegisterBindingModel userRegisterBindingModel) throws UserNotFoundException, UsernameExistsException {

        UserServiceModel userServiceModel = UserMapper.INSTANCE.userServiceBindingModelToUserServiceModel(userRegisterBindingModel);
        UserServiceModel newUser = userService.register(userServiceModel);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }



    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {

            String refreshToken = authorizationHeader.substring(TOKEN_PREFIX.length());
            String username = jwtTokenProvider.getSubject(refreshToken);
            if (jwtTokenProvider.isTokenValid(username, refreshToken)) {

                UserPrincipal user = new UserPrincipal(this.userService.findUserByUsername(username));
                String newAccessToken = jwtTokenProvider.generateJWTToken(user);
                Map<String, String> tokens = new HashMap<>();
                response.setHeader(JWT_TOKEN_HEADER, newAccessToken);
                response.setHeader(JWT_REFRESH_TOKEN_HEADER, refreshToken);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

}

