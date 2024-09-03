package com.example.demo;

import org.apache.pulsar.common.schema.SchemaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.pulsar.reactive.config.annotation.ReactivePulsarListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PulsarConsumer {


    private static final Logger log = LoggerFactory.getLogger(PulsarConsumer.class);

    @ReactivePulsarListener(topics = "test", schemaType = SchemaType.JSON)
    public Mono<Void> processMessage(MyMessageTest message) {
        return Mono.fromRunnable(() -> {
            try {
                log.info("Received message: {} from someTopic", message);
                // Process the message here
            } catch (Exception e) {
                log.error("Error processing message", e);
            }
        }).then();
    }
}
