package com.cos.security1.service;

import com.cos.security1.model.naver.NaverToken;
import com.cos.security1.model.naver.NaverUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class NaverOauthService implements OauthServiceInterface {

    @Value("${naver.client.secret}")
    private String secret;

    private final RestTemplate restTemplate;

    @Override
    public Object getToken(String code, String id, String url) {
        ResponseEntity<NaverToken> response = restTemplate.exchange(
                "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code" +
                "&client_id=" + id +
                "&client_secret=" + secret +
                "&code=" + code +
                "&state=" + "test",
                HttpMethod.GET,
                null,
                NaverToken.class
        );

        return response.getBody();
    }

    @Override
    public String getUserPk(String token) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(httpHeaders);

        ResponseEntity<NaverUserDetails> response = restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.GET,
                entity,
                NaverUserDetails.class
        );

        return response.getBody().getResponse().get("id");
    }

    public void getTokenFromNaver(String code, String id, String url) {
        NaverToken token = (NaverToken) getToken(code, id, url);
        String userPk = getUserPk(token.getAccess_token());

        System.out.println(token.getAccess_token());
        System.out.println(userPk);
    }
}
