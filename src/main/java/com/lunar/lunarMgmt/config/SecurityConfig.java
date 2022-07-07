package com.lunar.lunarMgmt.config;

import com.lunar.lunarMgmt.security.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // authorizeRequests()는 시큐리티 처리에 HttpServletRequest를 이용한다는 것을 의미합니다.
        // antMatchers()는 특정한 경로를 지정합니다.
        // permitAll()는 모든 사용자가 접근할 수 있다는 것을 의미합니다.
        http.authorizeRequests().antMatchers("/**").permitAll();

        // Cross-Site Http Request Disable(cross site 요청을 할수 없게 한다.)
        // csrf(Cross Site Request Forgery(위조된))사이트 변조 공격을 방어하기 위한 토큰 발급(매번 요청 마다 토큰을 발급하여 인증함, 그리하여 모든 화면에 있어야 됨)
        // Spring security는 기본적으로 csrf protection을 제공하여 csrf 공격으로 방지한다.
        // sessionManagement(세션관리),sessionCreationPolicy(sessionCreationPolicy.정책상수)
        // token으로 인증하기 발급되고 나서 캐시가 날라가지 않는 이상 계속 유지됨 그래서 아래와 같은 정책 사용함.
        http.cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 지정된 필터 앞에 커스텀 필터를 추가(UsernamePasswordAuthenticationFilter 보다 먼저 실행 된다.)
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(exceptionHandlerFilter, JwtRequestFilter.class);
    }
}
