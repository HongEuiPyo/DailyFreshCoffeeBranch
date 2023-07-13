package com.example.dailyFreshCoffeeBranch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@Configuration
@EnableJpaAuditing
public class AuditingConfig {

    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customize() {
        return pageableResolver -> {
            pageableResolver.setMaxPageSize(10);
            pageableResolver.setOneIndexedParameters(true);
        };
    }

}