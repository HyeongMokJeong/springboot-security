package com.cos.security1.model.kakao;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoToken {
    private String token_type;
    private String access_token;
    private long expires_in;
    private String refresh_token;
    private long refresh_token_expires_in;
    private String scope;
}
