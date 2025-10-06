package com.tasktracker.config;

import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.SystemMetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.web.tomcat.TomcatMetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = {
    SystemMetricsAutoConfiguration.class,
    TomcatMetricsAutoConfiguration.class,
    MetricsAutoConfiguration.class
})
public class MetricsConfig {
    // This configuration class disables all metrics-related auto configurations
    // to prevent container environment issues
}