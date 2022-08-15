package com.lunar.lunarMgmt.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    // 주로 복잡한 추상 클래스에서 사용하기 보다는
    // 단순한 interface기반으로 구현체를 주입을 할때 사용을 하면 좋을 것 같다.
}
