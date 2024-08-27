package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pulsar.client.api.*;
import org.springframework.stereotype.Service;

@Service
public class PulsarProducer {

    private final PulsarClient pulsarClient;
    private final ObjectMapper objectMapper;

    public PulsarProducer(PulsarClient pulsarClient) {
        this.pulsarClient = pulsarClient;
        this.objectMapper = new ObjectMapper();

    }

    public <T> void sendMessage(String topic, T message) throws PulsarClientException, JsonProcessingException {
        // Create a producer with a byte schema
        System.out.println("Sending message: " + message);

        try (Producer<byte[]> producer = pulsarClient.newProducer(Schema.BYTES)
                .topic(topic)
                .compressionType(CompressionType.ZSTD)

                .create()) {

            // Convert the message to JSON bytes
            byte[] jsonBytes = objectMapper.writeValueAsBytes(message);

            // Send the message
            producer.send(jsonBytes);
            System.out.println("Message sent to topic: " + topic + " with value: " + message);
        }
    }
}
