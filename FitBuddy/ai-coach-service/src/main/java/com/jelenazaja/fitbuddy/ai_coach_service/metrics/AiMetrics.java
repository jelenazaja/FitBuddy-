package com.jelenazaja.fitbuddy.ai_coach_service.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class AiMetrics {

    private final MeterRegistry meterRegistry;

    public AiMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void incrementRequest(String operation) {

        meterRegistry.counter(
                "fitbuddy.ai.requests",
                "operation", operation
        ).increment();
    }
}