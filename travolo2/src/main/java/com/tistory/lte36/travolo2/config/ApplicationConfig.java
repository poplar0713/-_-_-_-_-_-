package com.tistory.lte36.travolo2.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = { "com.tistory.lte36.travolo2.dao", "com.tistory.lte36.travolo2.serviceImpl"})
@Import({ DBConfig.class })
public class ApplicationConfig {

}