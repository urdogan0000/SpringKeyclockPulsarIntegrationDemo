package com.example.demo;


import com.liderahenkpulsar.auth.AuthenticationBasicAuth;
import org.apache.pulsar.client.api.PulsarClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class PulsarConfig {

    private static final Logger logger = LoggerFactory.getLogger(PulsarConfig.class);

    @Bean
    public PulsarClient pulsarClient() throws IOException {
        logger.info("try to connect");

        return PulsarClient.builder()
                .serviceUrl("pulsar://172.16.102.12:6650")
                .authentication(new AuthenticationBasicAuth("haydarTest", "haydar123"))
                .build();
    }
}
