package com.tasktracker.config;

import org.springframework.boot.actuate.autoconfigure.metrics.SystemMetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = {SystemMetricsAutoConfiguration.class})
public class MetricsConfig {
    // This configuration class disables SystemMetricsAutoConfiguration
    // to prevent container environment issues with ProcessorMetrics
}