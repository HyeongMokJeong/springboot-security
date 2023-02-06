package com.cos.jwt.auth.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwt.auth.PrincipalDetails;
import com.cos.jwt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;

// Security Session => Security ContextHolder : Authentication => UserDetails(PrincipalDetails)

// 스프링 시큐리티의 UsernamePasswordAuthenticationFilter 필터를 사용
// /login 요청해서 username, password 전송하면 (post)
// UsernamePasswordAuthenticationFilter 필터가 동작함
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 1. username, password 받아서
        ObjectMapper objectMapper = new ObjectMapper(); // JSON 데이터 파싱
        User user = null;
        try {
            user = objectMapper.readValue(request.getInputStream(), User.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 로그인을 위한 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        // 2. 정상인지 로그인 시도 - authenticationManager로 로그인 시도를 하면
        // PrincipalDetailsService 호출 -> loadUserByUsername 자동으로 실행됨
        // 정상이면 authentication 리턴됨 -> db에 있는 username과 password가 일치
        Authentication authentication =
                authenticationManager.authenticate(authenticationToken);

        // 3. PrincipalDetails를 세션에 담고 (권한 관리를 위해)
        // 로그인 성공
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("로그인 성공 : "+ principalDetails.getUser().getUsername());

        // return을 통해 authentication 객체가 session 영역에 저장됨
        // return 이유 -> 권한 관리를 security가 대신 해주기 때문에
        // JWT 사용하며 세션 만들 이유가 없지만 권한 처리 때문에 session에 저장
        return authentication;
    }

    // attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수 실행
    // JWT 토큰 생성하여 request 요청한 사용자에게 토큰 response
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication 실행됨 : 인증 완료");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        
        // HMAC256 방식
        String jwtToken = JWT.create()
                .withSubject("cos Token") // 토큰명
                        .withExpiresAt(new Date(System.currentTimeMillis() + JwtTemplate.EXPIRATION_TIME)) // 만료시간 (1000 = 1초)
                                .withClaim("id", principalDetails.getUser().getId())
                                        .withClaim("username", principalDetails.getUser().getUsername())
                                                .sign(Algorithm.HMAC256(JwtTemplate.SECRET)); // 서버 시크릿 키

        response.addHeader(JwtTemplate.HEADER_STRING, JwtTemplate.TOKEN_PREFIX + jwtToken);
    }
}
