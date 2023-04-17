package com.cos.security1.config.oauth;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.Person;
import com.google.api.services.people.v1.model.PhoneNumber;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        System.out.println("getClientRegistration : " + userRequest.getClientRegistration());
//        System.out.println("getAccessToken : " + userRequest.getAccessToken().getTokenValue());
//        System.out.println("getAttributes : " + super.loadUser(userRequest).getAttributes());

        Credential credential = new GoogleCredential().setAccessToken(userRequest.getAccessToken().getTokenValue());
        PeopleService peopleService = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName("springboot-oauth2")
                .build();
        PeopleService.People people = peopleService.people();
        try {
            PeopleService.People.Get get = people.get("people/me");
            get.setPersonFields("emailAddresses,phoneNumbers,addresses,genders");
            Person person = get.execute();
            System.out.println(person.getPhoneNumbers());
            System.out.println(person.toPrettyString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return super.loadUser(userRequest);
    }
}
