package com.lunar.lunarMgmt.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    // 수동 빈 주입은 interface 타입 만 되는 것 같다. abstract는 안되네..
    // 하나의 부모에 여러개의 sub를 한다면 어떻게 하나??
}
