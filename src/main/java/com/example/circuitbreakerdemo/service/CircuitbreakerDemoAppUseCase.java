package com.example.circuitbreakerdemo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.circuitbreakerdemo.infrastructure.circuitbreaker.CircuitBreakerTarget;
import com.example.circuitbreakerdemo.infrastructure.circuitbreaker.CircuitbreakerConfig;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;

/**
 * サーキットブレーカーが有効になった外部サービスを呼び出しを行うアプリケーションサービスクラスです。
 * 
 * <p>
 * 便宜上外部サービス呼び出しをアプリケーションサービスでやっていますが、
 * 真面目に実装するならば、リポジトリなどで外部呼び出しを行い、
 * アプリケーションサービスで外部サービスを呼び出していることを明記しない方がよいかもしれません。
 * </p>
 * 
 */
@Service
public class CircuitbreakerDemoAppUseCase {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final RestTemplate restTemplate;
  private final CircuitBreakerFactory circuitBreakerFactory;
  
  CircuitbreakerDemoAppUseCase(
    @Qualifier("default")final RestTemplate restTemplate,
    final CircuitBreakerFactory circuitBreakerFactory) {
    this.restTemplate = restTemplate;
    this.circuitBreakerFactory = circuitBreakerFactory;
  }

  /**
   * 基本的なサーキットブレーカーの使い方.
   * 
   * <p>
   * CircuitBreakerアノテーションを使っても同じことができるがそのやり方は省略
   * </p>
   * 
   * @return
   *  対抗サーバからのレスポンスボディ
   */
  public String callApi() {

    // Function<Throwable, String> fallBackFunction
    //   = t -> {
    //     logger.warn("fallBack Called!!", t);
    //     return "Errorあるよ";
    //   };

    return circuitBreakerFactory
      .create(CircuitBreakerTarget.backendA.name())
      .run(() -> restTemplate.getForObject("/api/external", String.class));
      // .run(() -> restTemplate.getForObject("/api/external", String.class), fallBackFunction);
  }
}
