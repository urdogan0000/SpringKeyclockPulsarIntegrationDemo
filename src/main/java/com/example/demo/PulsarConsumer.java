package com.example.demo;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.common.schema.SchemaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PulsarConsumer {

    private static final Logger log = LoggerFactory.getLogger(PulsarConsumer.class);
    private final ObjectMapper objectMapper;

    public PulsarConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PulsarListener(id = "test", subscriptionType = SubscriptionType.Shared, subscriptionName = "my-subscription", topics = "topic-asd", schemaType = SchemaType.BYTES)
    public void listen(byte[] message) {
        try {
            MyMessageTest myMessage = objectMapper.readValue(message, MyMessageTest.class);
            log.info("log getfrom test");
            if (Objects.equals(myMessage.getId(), "test")) {
                log.info("Received message: {} from test", myMessage);
            }

        } catch (Exception e) {
            log.error("Failed to deserialize message", e);

        }
    }


    @PulsarListener(id = "test1", subscriptionType = SubscriptionType.Shared, subscriptionName = "my-subscription1", topics = "topic-asd", schemaType = SchemaType.BYTES)
    public void listenTest(byte[] message) {
        try {
            log.info("log get from test1");
            MyMessageTest myMessage = objectMapper.readValue(message, MyMessageTest.class);
            if (Objects.equals(myMessage.getId(), "test1")) {
                log.info("Received message: {} from test1", myMessage);
            }

        } catch (Exception e) {
            log.error("Failed to deserialize message", e);

        }
    }

    @PulsarListener(id = "test2", subscriptionType = SubscriptionType.Shared, subscriptionName = "my-subscription2", topics = "topic-asd", schemaType = SchemaType.BYTES)
    public void listenTest2(byte[] message) {
        try {
            log.info("log get from test2");
            MyMessageTest myMessage = objectMapper.readValue(message, MyMessageTest.class);
            if (Objects.equals(myMessage.getId(), "test2")) {
                log.info("Received message: {} from test2", myMessage);
            }

        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Failed to deserialize message", e);
            }

        }
    }
}
