package com.cos.security1.service;

public interface OauthServiceInterface {
    Object getToken(String code, String key, String url);
    String getUserPk(String token);
}
