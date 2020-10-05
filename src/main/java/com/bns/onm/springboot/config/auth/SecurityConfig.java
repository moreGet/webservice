package com.bns.onm.springboot.config.auth;

import com.bns.onm.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정들을 활성화 시켜 줍니다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()
            .and() // h2-console 화면을 사용하기 위해 해당 옵션들을 disable 합니다.
                .authorizeRequests() // URL별 권한 관리를 설정하는 옵션의 시작점, 필수로 선언되어야만 anyMatchers 옵션 사용 가능
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll() // 누구나 허락
                // 아래 /api/v1/** 부터는 USER 권한만 가능
                .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // 권환 관리 대상을 지정하는 옵션, URL, HTTP 메소드 별로 관리가 가능
                .anyRequest().authenticated() // 설정된 값들 이외 나머지 URL들은 authenticated를 이용하여 인증된(로그인된) 사용자 모두에게 허용
            .and()
                .logout()
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("SESSION")
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/") // 로그아웃 성공시 index로 이동
                .permitAll()
            .and()
                .oauth2Login() // OAuth2 로그인 기능에 대한 여러 설정의 진입점
                .userInfoEndpoint() // Oauth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당.
                // 소셜 로그인 성공시 후속조치를 진행할 UserService 인터페이스의 구현체 등록
                // 리소스 서버(소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시
                .userService(customOAuth2UserService);
    }
}
