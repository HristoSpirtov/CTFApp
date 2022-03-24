package com.ctf.ctfserver.security;

import com.ctf.ctfserver.constant.SecurityConstant;
import com.ctf.ctfserver.filter.JWTAccessDeniedHandler;
import com.ctf.ctfserver.filter.JWTAuthenticationEntryPoint;
import com.ctf.ctfserver.filter.JWTAuthenticationFilter;
import com.ctf.ctfserver.filter.JWTAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class securityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    private final JWTAuthorizationFilter jwtAuthorizationFilter;
    private final JWTAccessDeniedHandler jwtAccessDeniedHandler;
    private final JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JWTAuthenticationFilter customAuthenticationFilter = new JWTAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(SecurityConstant.PUBLIC_URLS).permitAll();
        http.authorizeRequests().antMatchers(GET, "api/user/**").hasAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(POST, "api/user/save/**").hasAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler).authenticationEntryPoint(jwtAuthenticationEntryPoint);
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
