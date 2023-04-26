package com.cos.security1.model.google;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GoogleToken {
    private String access_token;
    private Integer expires_in;
    private String id_token;
    private String scope;
    private String token_type;
}
