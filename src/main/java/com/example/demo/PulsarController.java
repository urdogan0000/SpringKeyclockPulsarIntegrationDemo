package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pulsar")
public class PulsarController {

    private static final Logger logger = LoggerFactory.getLogger(PulsarController.class);
    private final PulsarProducer pulsarProducer;
    private final PulsarListenerManager pulsarListenerManager;

    public PulsarController(PulsarProducer pulsarProducer, PulsarListenerManager pulsarListenerManager) {
        this.pulsarProducer = pulsarProducer;
        this.pulsarListenerManager = pulsarListenerManager;
    }

    @GetMapping("/send")
    public void sendMessage() {
        try {
            logger.info("Sending message");
            MyMessageTest myMessage = new MyMessageTest();
            myMessage.setId("test");
            myMessage.setContent("test");

            pulsarProducer.sendMessage("topic-asd", myMessage);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }

    }

    @GetMapping("/stop")
    public void stopPulsarListener() {
        try {
            pulsarListenerManager.removeListeners();
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

    }

    @GetMapping("/start")
    public void startPulsarListener() {
        try {
            pulsarListenerManager.addListeners();
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

    }
}