package com.springboot.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class JwtTokenizer {
    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
        //base64타입으로 변환 얘는 4바이트 한국
    }

    // 인증된 사용자에게 JWT를 최초 발금 하기위한 생성 메서드 즉 access 토큰 주는 코드
    public String generateAccessToken(Map<String, Object> claims,
                                      String subject,
                                      Date expiration,
                                      String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);
        return Jwts.builder()
                .setClaims(claims)  //claims 에는 인증된 사용자 정보 추가
                .setSubject(subject) // JWT에 대한 제목을 추가
                .setIssuedAt(Calendar.getInstance().getTime()) // JWT 발행 일자를 설정
                .setExpiration(expiration) // JWT의 만료일시를 지정
                .signWith(key) //서명을 위한 Key(java.security.Key) 객체를 설정
                .compact(); //compact()를 통해 JWT를 생성하고 직렬화

    }
    //리프레쉬 토큰 생성
    public String generateRefreshToken (String subject,Date expiration,String base64EncodedSecretKey){
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();

    }


    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64URL.decode(base64EncodedSecretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return key;

    }

}
