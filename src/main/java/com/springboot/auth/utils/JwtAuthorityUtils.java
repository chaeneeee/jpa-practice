package com.springboot.auth.utils;

import com.springboot.like.entity.Like;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class JwtAuthorityUtils {
    @Value("${mail.address.admin}")  //애플리케이션 속성 파일에서 값을 주입받기 위해 사용
    private String adminMailAddress;
//관리자 사용자 역할을 미리 정의한 목록 Spring Security 의 AuthorityUtils 를 사용하여 만듭니다.
    private final List<GrantedAuthority> ADMIN_ROLES = AuthorityUtils.createAuthorityList("ROLE_ADMIN","ROLE_USER");
    private final List<GrantedAuthority> USER_ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");
   //역할을 문자열로 미리 정의한 목록
    private final List<String> ADMIN_RILES_STRING = List.of("ADMIN","USER");
    private final List<String> USER_ROLES_STRING = List.of("USER");
//Admin 일치하면 admin role 반환 그렇지 않으면 userRoles 반환
    public List<GrantedAuthority> createAuthorities(String email) {
        if (email.equals(adminMailAddress)) {
            return ADMIN_ROLES;
        }
        return USER_ROLES;
    }

    public List<GrantedAuthority> createAuthorities (List<String> roles) {
        return roles.stream()
                .map(role-> new SimpleGrantedAuthority("ROLE" + role))
                .collect(Collectors.toList());
    }

    public List<String> createRole(String email) {
        if (email.equals(adminMailAddress)) {
            return ADMIN_RILES_STRING;
        }
        return USER_ROLES_STRING;
    }
}
