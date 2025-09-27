package com.example.circuitbreakerdemo.infrastructure.httpclient;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
class RestTemplateConfig {

  @Bean
  @Qualifier("default")
  RestTemplate restTemplate() {
    return new RestTemplateBuilder().rootUri("http://localhost:9090").build();
  }
}
