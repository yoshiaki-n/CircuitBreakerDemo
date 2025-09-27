package com.example.circuitbreakerdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.circuitbreakerdemo.service.CircuitbreakerDemoAppUseCase;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
class CircuitbreakerDemoAppController {

  private final CircuitbreakerDemoAppUseCase useCase;

  CircuitbreakerDemoAppController(final CircuitbreakerDemoAppUseCase useCase) {
    this.useCase = useCase;
  }

  @GetMapping("/circuit-breaker")
  String curcuitBreakerApi() {
      return useCase.callApi();
  }
  
}
