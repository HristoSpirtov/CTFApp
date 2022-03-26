package com.ctf.ctfserver.api;


import com.ctf.ctfserver.domain.entities.User;
import com.ctf.ctfserver.domain.models.mapper.UserMapper;
import com.ctf.ctfserver.domain.models.binding.UserRegisterBindingModel;
import com.ctf.ctfserver.domain.models.service.UserServiceModel;
import com.ctf.ctfserver.exception.ExceptionHandling;
import com.ctf.ctfserver.exception.domain.UserNotFoundException;
import com.ctf.ctfserver.exception.domain.UsernameExistsException;
import com.ctf.ctfserver.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static com.ctf.ctfserver.constant.SecurityConstant.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/api", "/"}) //include "/" for the error controller to map the /error path correctly!!!
public class UserController extends ExceptionHandling {

    private final UserService userService;

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





//    @PostMapping("/role/addtouser")
//    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserForm form) {
//        userService.addRoleToUser(form.getUsername(), form.getRoleName());
//        return ResponseEntity.ok().build();
//    }

//    @GetMapping("/token/refresh")
//    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String authorizationHeader = request.getHeader(AUTHORIZATION);
//        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
//            try {
//                String refreshToken = authorizationHeader.substring(TOKEN_PREFIX.length());
//                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
//                JWTVerifier verifier = JWT.require(algorithm).build();
//                DecodedJWT decodedJWT = verifier.verify(refreshToken);
//                String username = decodedJWT.getSubject();
//                User user = userService.findUserByUsername(username);
//                String accessToken = JWT.create()
//                        .withSubject(user.getUsername())
//                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
//                        .withIssuer(request.getRequestURL().toString())
//                        .withClaim("roles", user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()))
//                        .sign(algorithm);
//
//                Map<String, String> tokens = new HashMap<>();
//                tokens.put("access_token", accessToken);
//                tokens.put("refresh_token", refreshToken);
//                response.setContentType(APPLICATION_JSON_VALUE);
//                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
//
//
//            } catch (Exception exception) {
//                response.setHeader("error", exception.getMessage());
//                response.setStatus(FORBIDDEN.value());
//                //response.sendError(FORBIDDEN.value());
//                Map<String, String> error = new HashMap<>();
//                error.put("error_message", exception.getMessage());
//                response.setContentType(APPLICATION_JSON_VALUE);
//                new ObjectMapper().writeValue(response.getOutputStream(), error);
//            }
//        } else {
//            throw new RuntimeException("Refresh token is missing");
//        }
//    }

}

