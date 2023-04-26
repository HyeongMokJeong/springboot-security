package com.cos.security1.service;

import com.cos.security1.model.kakao.KakaoToken;
import com.cos.security1.model.kakao.KakaoUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KakaoOauthService implements OauthServiceInterface {
    private final RestTemplate restTemplate;

    @Override
    public String getToken(String code, String key, String url) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", key);
        body.add("redirect_uri", url);
        body.add("code", code);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Host", "kauth.kakao.com");
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<KakaoToken> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                entity,
                KakaoToken.class
        );

        return response.getBody().getAccess_token();
    }

    @Override
    public String getUserPk(String token) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Host", "kapi.kakao.com");
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        httpHeaders.add("Authorization", "Bearer " + token);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(httpHeaders);

        ResponseEntity<KakaoUserDetails> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                entity,
                KakaoUserDetails.class
        );

        return response.getBody().getId();
    }

    public void getTokenFromKakao(String code, String key, String url) {
        String token = getToken(code, key, url);
        String pk = getUserPk(token);

        System.out.println(token);
        System.out.println(pk);
    }
}
