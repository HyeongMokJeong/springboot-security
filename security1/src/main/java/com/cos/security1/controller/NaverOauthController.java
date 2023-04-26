package com.cos.security1.controller;

import com.cos.security1.service.NaverOauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class NaverOauthController {

    private final NaverOauthService service;

    @Value("${naver.client.id}")
    private String naverId;

    @Value("${naver.client.url}")
    private String naverUrl;

    @GetMapping("/login/naver")
    public String loginToGoogle() {
        return "redirect:https://nid.naver.com/oauth2.0/authorize?" +
                "response_type=code" +
                "&client_id=" + naverId +
                "&redirect_uri=" + naverUrl +
                "&state=" + "test";
    }

    @GetMapping("/login/oauth2/naver/redirect")
    public String redirectToKakao(@RequestParam String code) {
        service.getTokenFromNaver(code, naverId, naverUrl);
        return "login success";
    }
}
