package com.ctf.ctfserver.constant;

public class SecurityConstant {
    public static final long EXPIRATION_TIME = 432_000_000;  // 5days expressed in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String GET_CTFA = "CTFa";
    public static final String GET_CTFA_CLIENT = "CTFa client";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to login to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = {"/app/login/**", "app/register", "/app/resetpassword/**", "/api/token/refresh/**"};


}