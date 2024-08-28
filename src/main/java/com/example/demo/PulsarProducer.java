package com.example.demo;


import org.apache.pulsar.client.api.CompressionType;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PulsarProducer {
    private static final Logger logger = LoggerFactory.getLogger(PulsarProducer.class);
    private final PulsarClient pulsarClient;


    public PulsarProducer(PulsarClient pulsarClient) {
        this.pulsarClient = pulsarClient;

    }

    public <T> void sendMessage(String topic, T message) throws PulsarClientException {

        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) message.getClass();

        try (Producer<T> producer = pulsarClient.newProducer(JSONSchema.of(clazz))
                .topic(topic)
                .compressionType(CompressionType.ZSTD)
                .create()) {

            // Send the message
            producer.send(message);
            logger.info("Message sent to topic: %s with value: %s".formatted(topic, message));
        }
    }
}
