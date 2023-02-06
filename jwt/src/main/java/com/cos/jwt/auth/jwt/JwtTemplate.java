package com.cos.jwt.auth.jwt;

public interface JwtTemplate {
    String SECRET = "secretKey"; // 서버 비밀 값
    int EXPIRATION_TIME = 60000 * 10;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
