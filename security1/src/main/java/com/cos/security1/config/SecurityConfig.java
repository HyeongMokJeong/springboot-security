package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Spring Security Filter(SecurityConfig)가 기본 Spring filter chain에 등록됨
public class SecurityConfig {

    @Bean // 해당 메소드의 리턴 오브젝트를 ioc로 등록
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests(authorize
                -> {
            try {
                authorize.requestMatchers("/user/**").authenticated()
                .requestMatchers("/manager/**").hasAnyRole("ROLE_MANAGER", "ROLE_ADMIN")
                .requestMatchers("/admin/**").hasRole("ROLE_ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/loginForm");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return http.build();
    }

}
