package com.cos.security1.service;

import com.cos.security1.model.google.GoogleToken;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class GoogleOauthService implements OauthServiceInterface {
    private final RestTemplate restTemplate;

    @Value("${google.client.secret}")
    private String secret;

    @Override
    public Object getToken(String code, String id, String url) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Host", "oauth2.googleapis.com");
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", code);
        body.add("client_id", id);
        body.add("client_secret", secret);
        body.add("redirect_uri", url);
        body.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<GoogleToken> response = restTemplate.exchange(
                "https://oauth2.googleapis.com/token",
                HttpMethod.POST,
                entity,
                GoogleToken.class
        );

        return response.getBody();
    }

    @Override
    public String getUserPk(String token) {
        String[] jwtParts = token.split("\\.");
        String encodedBody = jwtParts[1];
        byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedBody);
        String decodedBody = new String(decodedBytes);
        JsonObject jsonObject = JsonParser.parseString(decodedBody).getAsJsonObject();
        return jsonObject.get("sub").getAsString();
    }

    public void getTokenFromGoogle(String code, String id, String url) {
        GoogleToken token = (GoogleToken) getToken(code, id, url);
        String accessToken = token.getAccess_token();
        String userPk = getUserPk(token.getId_token());

        System.out.println(accessToken);
        System.out.println(userPk);
    }
}
