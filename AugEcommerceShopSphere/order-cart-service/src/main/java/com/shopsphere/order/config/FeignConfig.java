package com.shopsphere.order.config;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    // 1. Pull the secret from application.properties
    @Value("${shopsphere.internal.secret}")
    private String internalSecret;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // 2. Flash the admin badge...
                template.header("X-User-Role", "ADMIN");

                // 3. ...and provide the secret password!
                template.header("X-Internal-Secret", internalSecret);
            }
        };
    }
}
