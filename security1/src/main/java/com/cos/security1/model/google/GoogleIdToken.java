package com.cos.security1.model.google;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GoogleIdToken {
    private String aud;
    private String exp;
    private String iat;
    private String iss;
    private String sub;
}
