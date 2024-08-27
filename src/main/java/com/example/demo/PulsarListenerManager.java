package com.example.demo;

import org.springframework.pulsar.config.PulsarListenerEndpointRegistry;
import org.springframework.stereotype.Component;


@Component
public class PulsarListenerManager  {

    private final PulsarListenerEndpointRegistry registry;

    public PulsarListenerManager(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") PulsarListenerEndpointRegistry registry) {
        this.registry = registry;
    }


    public void addListeners() {
        registry.start(); // Start all listeners
    }


    public void removeListeners() {
        registry.stop(); // Stop all listeners
    }
}
