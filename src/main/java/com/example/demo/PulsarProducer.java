package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.pulsar.reactive.core.ReactivePulsarTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PulsarProducer {

    private static final Logger logger = LoggerFactory.getLogger(PulsarProducer.class);

    private final ReactivePulsarTemplate<Object> pulsarTemplate;

    public PulsarProducer(ReactivePulsarTemplate<Object> pulsarTemplate) {
        this.pulsarTemplate = pulsarTemplate;
    }

    public <T> Mono<Void> sendMessage(String topic, T message) {
        return pulsarTemplate.send(topic, message)
                .doOnSuccess(aVoid -> logger.info("Message sent to topic: {} with value: {}", topic, message))
                .doOnError(e -> logger.error("Failed to send message to topic: {} with value: {}", topic, message, e))
                .then();
    }
}
