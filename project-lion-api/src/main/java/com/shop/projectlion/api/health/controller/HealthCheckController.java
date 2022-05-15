package com.shop.projectlion.api.health.controller;

import com.shop.projectlion.api.health.dto.HealthCheckResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class HealthCheckController {

    @GetMapping("/health")
    public ResponseEntity<HealthCheckResponseDto> getServerStatus() {
        HealthCheckResponseDto healthCheckResponseDto = HealthCheckResponseDto.builder()
                .status(true)
                .health("ok")
                .build();

        return ResponseEntity.ok(healthCheckResponseDto);
    }
}
