package com.lunar.lunarMgmt.common.config;

import com.lunar.lunarMgmt.common.sercurity.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Slf4j
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/**").permitAll();// antMatchers 설정한 리소스의 접근을 인증절차 없이 허용한다는 의미.

        http.cors().disable()// localhost:3000->localhost:8080으로 요청 시 저 옵션이 활성화 되면 통신이 안됨.
                .csrf().disable()// post, put, delete요청으로부터 위조된 정보에 대해서 보호해주는데 crsf.token값이 전부 있어야 한다. 이 기능은 지금 사용 안해서 disable
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);// 스프링시큐리티가 생성하지도 않고 기존것을 사용하지도 않음 즉 JWT 같은 토큰 방식을 쓸 때 사용하는 설정

        // UsernamePasswordAuthenticationFilter가 걸리기 전에 jwtRequestFilter가 걸린다.
        // UsernamePasswordAuthenticationFilter란 Form based Authentication 방식으로 인증을 진행할 때 아이디, 패스워드 데이터를 파싱하여 인증 요청을 위임하는 필터
        // 기본적으로 세션 쿠키 방식의 인증이 이루어진다. 이 인증이 이루어지는 Filter는 UsernamePasswordAuthenticationFilter 클래스다.
        // 지금 이 사이트에서는 사용을 하지 않는다. 그래서 기본형을 필터가 사용되기전 jwtRequestFilter를 사용.
        // 그래서 BasicAuthenticationFilter.class로 교체 함.
        http.addFilterBefore(jwtRequestFilter, BasicAuthenticationFilter.class);
    }
}
