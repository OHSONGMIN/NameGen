package com.spring.namegen.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OpenAiConfig {

    @Value("${openai.api.key}")
    private String openAiKey;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .additionalInterceptors((request, body, execution) -> {
                    request.getHeaders().add("Authorization", "Bearer " + openAiKey);
                    return execution.execute(request, body);
                })
                .build();
    }
}
