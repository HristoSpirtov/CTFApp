package com.ctf.ctfserver.filter;


import com.ctf.ctfserver.domain.UserPrincipal;
import com.ctf.ctfserver.domain.models.mapper.UserMapper;
import com.ctf.ctfserver.domain.models.response.UserResponseModel;
import com.ctf.ctfserver.utility.JWTTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


import static com.ctf.ctfserver.constant.SecurityConstant.*;


@Slf4j
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

        private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        Map map = null;
        try {
            map = gson.fromJson(request.getReader().readLine(), Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String username = (String) map.get("username");
        String password = (String) map.get("password");
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

        UserResponseModel userResponseModel = UserMapper.INSTANCE.userToUserResponseModel(userPrincipal.getUser());

        response.setHeader(JWT_TOKEN_HEADER, accessToken);
        response.setHeader(JWT_REFRESH_TOKEN_HEADER, refreshToken);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), userResponseModel);

    }
}
