package com.example.demo;

import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.common.schema.SchemaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.stereotype.Service;

@Service
public class PulsarConsumer {

    
    private static final Logger log = LoggerFactory.getLogger(PulsarConsumer.class);

    @PulsarListener(id = "test", subscriptionType = SubscriptionType.Shared, subscriptionName = "my-subscription", topics = "topic-asd", schemaType = SchemaType.JSON)
    public void listen(MyMessageTest message) {
        try {

            log.info("Received message: {} from test", message);


        } catch (Exception e) {
            log.error("Failed to deserialize message", e);

        }
    }
}
