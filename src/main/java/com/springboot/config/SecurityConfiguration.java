package com.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;


@Configuration
public class SecurityConfiguration {
    //http 요청에 대한 보안 필터 및 권한 설정을 구성
    //필터체인은 웹 애플리케이션에서 HTTP 요청을 처리할 때 요청과 응답 사이 여러 필터를 연결
    //필터체인 역할 인증 인가 보안 관련 설정 적용 사용자 정의 필터 추가  (인증 / 인가 차이 )
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
         //헤더 구성 - 동일 출처에서 iframe 페이지를 랜더링을 할 수 있도록 헤더 설정
                .headers().frameOptions().sameOrigin()
                .and()
                // CSRF(Cross-Site Request Forgery) 공격에 대한 보호 기능을 비활성화합니다
                .csrf().disable() //이거 설정 x 403 에러
                // 폼 기반 인증을 비활성화합니다
                .cors(Customizer.withDefaults())
                //cors 설정 추가
                //.cors(withDefaults()) 일 경우 corsConfigurationSource 라는 이름으로 등록된 Bean 사용
                .formLogin().disable() //json 포맷으로 password 전송하는 방식 사용
                // HTTP 기본 인증을 비활성화
                .httpBasic().disable()
                // HTTP 요청에 대한 권한 규칙을 설정
                .authorizeHttpRequests(authorize -> authorize
                         // 모든 요청을 인증 없이 허용
                        .anyRequest().permitAll());

    return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); //모든 출처(Origin)에 대해 스크립트 기반의 HTTP 통신을 허용
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;

    }
}
