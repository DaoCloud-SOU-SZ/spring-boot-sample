package com.example.demo.utils;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import com.example.demo.utils.LoadProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 
import java.time.Duration;
 
@Configuration
@EnableConfigurationProperties(LoadProperties.class)
public class PrometheusHistogramsConfig {
    @Autowired
    private LoadProperties loadProperties;
 
    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> {
            registry.config().meterFilter(
                    new MeterFilter() {
                        @Override
                        public DistributionStatisticConfig configure(Meter.Id id, DistributionStatisticConfig config) {
                            if (id.getType() == Meter.Type.TIMER && id.getName().matches(loadProperties.getRegex())) {
                                return DistributionStatisticConfig.builder()
                                        .percentilesHistogram(true)
                                        .percentiles(0.5, 0.90, 0.95, 0.99).serviceLevelObjectives(Duration.ofMillis(50).toNanos(),
                                                Duration.ofMillis(100).toNanos(),
                                                Duration.ofMillis(200).toNanos(),
                                                Duration.ofSeconds(1).toNanos(),
                                                Duration.ofSeconds(5).toNanos())
                                        .minimumExpectedValue(Double.longBitsToDouble(Duration.ofMillis(1).toNanos()))
                                        .maximumExpectedValue(Double.longBitsToDouble(Duration.ofMillis(5).toNanos()))
                                        .build()
                                        .merge(config);
                            } else {
                                return config;
                            }
                        }
                    });
        };
    }
}