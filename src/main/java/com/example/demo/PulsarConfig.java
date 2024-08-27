package com.example.demo;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.impl.auth.oauth2.AuthenticationFactoryOAuth2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Configuration
public class PulsarConfig {

    @Value("${pulsar.issuer-url}")
    private String issuerUrlString;

    @Value("${pulsar.credentials-url}")
    private String credentialsUrlString;

    @Value("${pulsar.audience}")
    private String audience;

    @Value("${spring.pulsar.client.service-url}")
    private String serviceUrl;

    @Bean
    public PulsarClient pulsarClient() throws IOException {

        URL issuerUrl = new URL(issuerUrlString);
        URL credentialsUrl = new URL(credentialsUrlString);

        System.out.println("PulsarClient initializing...");

        // Build Pulsar Client with OAuth2 Authentication
        return PulsarClient.builder()
                .serviceUrl(serviceUrl)
                .authentication(AuthenticationFactoryOAuth2.clientCredentials(issuerUrl, credentialsUrl, audience))
                .operationTimeout(30, TimeUnit.SECONDS)
                .connectionTimeout(30, TimeUnit.SECONDS)
                .build();


    }

}
