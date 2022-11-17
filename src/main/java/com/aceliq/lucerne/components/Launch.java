package com.aceliq.lucerne.components;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class},
    scanBasePackages = {"com.aceliq.lucerne.components", "com.aceliq.lucerne.config",
        "com.aceliq.lucerne.controller", "com.aceliq.lucerne.data", "com.aceliq.lucerne.model",
        "com.aceliq.lucerne.core"})
@EnableJpaRepositories("com.aceliq.lucerne.data")
@EntityScan(basePackages = "com.aceliq.lucerne.model")
public class Launch extends SpringBootServletInitializer {
  public static void main(String[] args) {
    SpringApplication.run(Launch.class, args);
  }
}
