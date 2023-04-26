package com.cos.security1.controller;

import com.cos.security1.service.GoogleOauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class GoogleOauthController {

    private final GoogleOauthService service;

    @Value("${google.client.id}")
    private String googleId;

    @Value("${google.client.url}")
    private String googleUrl;

    @GetMapping("/login/google")
    public String loginToGoogle() {
        return "redirect:https://accounts.google.com/o/oauth2/auth?" +
                "client_id=" + googleId +
                "&redirect_uri=" + googleUrl +
                "&response_type=code +" +
                "&scope=openid profile email";
    }

    @GetMapping("/login/oauth2/code/google")
    public void redirectToGoogle(@RequestParam String code) {
        service.getTokenFromGoogle(code, googleId, googleUrl);
    }
}
