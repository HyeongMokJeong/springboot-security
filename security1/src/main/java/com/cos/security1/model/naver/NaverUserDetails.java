package com.cos.security1.model.naver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NaverUserDetails {
    private String resultcode;
    private String message;
    private Map<String, String> response;
}
