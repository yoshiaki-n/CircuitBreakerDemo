package com.example.circuitbreakerdemo;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.client.HttpServerErrorException.ServiceUnavailable;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CircuitbreakerdemoApplicationTests {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @RegisterExtension
  static WireMockExtension EXTERNAL_SERVICE = WireMockExtension.newInstance()
      .options(WireMockConfiguration.wireMockConfig().port(9090))
      .build();

  private TestRestTemplate restTemplate;

  @Autowired
  public CircuitbreakerdemoApplicationTests(final TestRestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Test
  void testCircuitBreaker() {
    // 準備
    EXTERNAL_SERVICE.stubFor(WireMock.get("/api/external").willReturn(serverError()));
    final List<ResponseEntity<String>> closeCircuitResponses = new ArrayList<>();
    final List<ResponseEntity<String>> openCircuitResponses = new ArrayList<>();

    // 実行
    IntStream.rangeClosed(1, 5)
      .forEach(i-> {
        closeCircuitResponses.add(restTemplate.getForEntity("/api/circuit-breaker", String.class));
      });
    IntStream.rangeClosed(1, 5)
      .forEach(i-> {
        openCircuitResponses.add(restTemplate.getForEntity("/api/circuit-breaker", String.class));
      });

    // 検証
    EXTERNAL_SERVICE.verify(5, getRequestedFor(urlEqualTo("/api/external")));
    assertThat(closeCircuitResponses).hasSize(5);
    closeCircuitResponses.forEach(res -> assertThat(res.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR));
    assertThat(openCircuitResponses).hasSize(5);
    openCircuitResponses.forEach(res -> assertThat(res.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR));

  }

}
