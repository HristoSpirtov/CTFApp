package com.ctf.ctfserver.filter;


import com.ctf.ctfserver.domain.UserPrincipal;
import com.ctf.ctfserver.domain.models.mapper.UserMapper;
import com.ctf.ctfserver.domain.models.response.UserResponseModel;
import com.ctf.ctfserver.utility.JWTTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.mapstruct.Mapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import static com.ctf.ctfserver.constant.SecurityConstant.*;


@Slf4j
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;


    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
//        Gson gson = new Gson();
//        Map map = gson.fromJson(request.getReader().readLine(), Map.class);
//        String username = (String) map.get("username");
//        String password = (String) map.get("password");
        log.info("Username is: {}", username);
        log.info("Password is: {}", password);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        String accessToken = jwtTokenProvider.generateJWTToken(userPrincipal);
        String refreshToken = jwtTokenProvider.generateJWTRefreshToken(userPrincipal);
//        HttpResponse httpResponse = new HttpResponse(
//                new Date(),
//                HttpStatus.CREATED.value(),
//                HttpStatus.CREATED,
//                HttpStatus.CREATED.getReasonPhrase().toUpperCase(),
//                "User successfully logged in".toUpperCase());

        UserResponseModel userResponseModel = UserMapper.INSTANCE.userToUserResponseModel(userPrincipal.getUser());

        response.setHeader(JWT_TOKEN_HEADER, accessToken);
        response.setHeader(JWT_REFRESH_TOKEN_HEADER, refreshToken);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), userResponseModel);

    }


}
