package com.lunar.lunarMgmt.common.config.yml;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "menu-file")
@Data
public class MenuFileConfig {
    private String uploadPath;
}
