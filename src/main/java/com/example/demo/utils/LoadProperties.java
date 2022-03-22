package com.example.demo.utils
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
 
@Component
@ConfigurationProperties(prefix = "prometheus-histogram-metrics")
public class LoadProperties {
    public String getRegex() {
        return regex;
    }
 
    public void setRegex(String regex) {
        this.regex = regex;
    }
 
    private String regex;
}
