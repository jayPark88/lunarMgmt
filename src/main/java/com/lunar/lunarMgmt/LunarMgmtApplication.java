package com.lunar.lunarMgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EntityScan(basePackages = {"com.lunar.lunarMgmt.common.jpa.entities"})
@EnableJpaRepositories(basePackages = {"com.lunar.lunarMgmt.common.jpa.repository"})
@EnableJpaAuditing
@SpringBootApplication
public class LunarMgmtApplication {
  public static void main(String[] args) {
    SpringApplication.run(LunarMgmtApplication.class, args);
  }
}
