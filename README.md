# Springboot-Security

스프링부트 시큐리티 & JWT 실습 코드입니다.  


## Spring Security
Filter 기반으로 인증과 권한을 처리하는 보안 담당 Spring 하위 프레임워크

- 사용 이유  
1. 다른 Spring 프로젝트, 프레임워크와 쉽게 통합됨  
2. 검증되고 안전한 인증 및 권한 부여 가능  
3. 일반적인 공격에 대한 보호 가능  
4. 관심사 분리 가능

- Filter 처리 구간
<img src="https://github.com/HBNU-SWUNIV/come-capstone23-kjj/assets/94634916/18b11cc0-6a01-4208-bb31-e3f8c50f578b">  

- 인증 절차
<img src="https://github.com/HBNU-SWUNIV/come-capstone23-kjj/assets/94634916/9f3326f6-1ede-47da-97f9-e86f7ed4918a">

## OAuth2.0
인증을 위한 개방형 표준 프로토콜  
Resource Owner를 대신하여 해당 Resource 접근 권한을 위임받을 수 있는 프로토콜이다.  

구현 방식에 차이는 있으나, 기본적인 동작 메커니즘은 다음과 같다.
1. Resource Owner가 Login 및 권한 위임 동의
2. Authorization Code(인가 코드) 발급
3. 해당 Authorization Code로 Access Token 발급
4. Access Token으로 Resource 접근  

- 구현예)
<img src="https://github.com/HBNU-SWUNIV/come-capstone23-kjj/assets/94634916/89029ee1-ce81-4011-99ce-b9fa7f1d5310">

## JWT
Json Web Token
- Header / Payload / Signature 3 Parts로 구분
- 무상태성, 트래픽 부담 적음
- Header와 Payload는 Base64Url로 Encode -> Decode 가능하여 민감 정보 사용 불가
- 상대적으로 탈취에 취약 -> Refresh Token, BlackList 등 도입 고민 필요
- Request마다 포함되어 데이터 크기에 영향을 미칠 수 있음


## 참고 강의
인프런 최주호님
