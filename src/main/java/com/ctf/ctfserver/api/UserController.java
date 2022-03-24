package com.ctf.ctfserver.api;


import com.ctf.ctfserver.domain.User;
import com.ctf.ctfserver.exception.ExceptionHandling;
import com.ctf.ctfserver.exception.domain.UserNotFoundException;
import com.ctf.ctfserver.exception.domain.UsernameExistsException;
import com.ctf.ctfserver.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/api", "/"}) //include "/" for the error controller to map the /error path correctly!!!
public class UserController extends ExceptionHandling {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>>getUsers() throws UserNotFoundException {
        throw new UserNotFoundException("This user was not found");
//        return ResponseEntity.ok().body(userService.getUsers());
    }

//    @PostMapping("/user/save")
//    public ResponseEntity<User>saveUser(@RequestBody User user) {
//        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/save").toUriString());
//        return ResponseEntity.created(uri).body(userService.saveUser(user));
//    }

//    @PostMapping("/role/save")
//    public ResponseEntity<Role>saveRole(@RequestBody Role role) {
//        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/save").toUriString());
//        return ResponseEntity.created(uri).body(userService.saveRole(role));
//    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws UserNotFoundException, UsernameExistsException {

        User newUser = userService.register(user.getName(), user.getUsername());
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }





//    @PostMapping("/role/addtouser")
//    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserForm form) {
//        userService.addRoleToUser(form.getUsername(), form.getRoleName());
//        return ResponseEntity.ok().build();
//    }

//    @GetMapping("/token/refresh")
//    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String authorizationHeader = request.getHeader(AUTHORIZATION);
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            try {
//                String refreshToken = authorizationHeader.substring("Bearer ".length());
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

@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}
