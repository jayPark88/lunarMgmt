package com.lunar.lunarMgmt.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    // 추상 클래스에서도 사용이 가능
    // 여기에 @RequiredArgsConstructor 생성자 주입을 하여 repository선언하여 주입을 지정 할 수 있다.
}
