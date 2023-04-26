package com.cos.security1.controller;

import com.cos.security1.service.KakaoOauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class KaKaoOauthController {

    private final KakaoOauthService service;

    @Value("${kakao.key}")
    private String kakaoKey;

    @Value("${kakao.url}")
    private String kakaoUrl;

    @GetMapping("/login/kakao")
    public String loginToKakao() {
        return "redirect:https://kauth.kakao.com/oauth/authorize?" +
                "response_type=code"
                +"&client_id=" + kakaoKey +
                "&redirect_uri=" + kakaoUrl;
    }

    @GetMapping("/login/oauth2/kakao/redirect")
    public String redirectToKakao(@RequestParam(name = "code", required = false) String code, @RequestParam(name = "error", required = false) String error) {
        service.getTokenFromKakao(code, kakaoKey, kakaoUrl);
        return "login success";
    }
}
