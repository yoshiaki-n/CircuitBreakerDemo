package com.example.circuitbreakerdemo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.autoconfigure.properties.SkipPropertyMapping;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CircuitbreakerdemoApplicationTests {

  @RegisterExtension
  static WireMockExtension EXTERNAL_SERVICE = WireMockExtension.newInstance()
      .options(WireMockConfiguration.wireMockConfig().port(9090))
      .build();

  @Test
  void contextLoads() {
  }

}
