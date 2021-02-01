package com.company.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "searcher")
public class SearcherConfig {
    private boolean ignoreCase;
    private int columnNumber;

    @Bean
    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    @Bean
    public int getColumnNumber() {
        return columnNumber;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }
}
