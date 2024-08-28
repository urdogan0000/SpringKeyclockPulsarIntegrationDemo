package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pulsar")
public class PulsarController {

    private final PulsarProducer pulsarProducer;
    private final PulsarListenerManager pulsarListenerManager;

    public PulsarController(PulsarProducer pulsarProducer, PulsarListenerManager pulsarListenerManager) {
        this.pulsarProducer = pulsarProducer;
        this.pulsarListenerManager = pulsarListenerManager;
    }

    @GetMapping("/send")
    public void sendMessage() {
        try {
            System.out.println("Sending message");
            MyMessageTest myMessage = new MyMessageTest();
            myMessage.setId("test");
            myMessage.setContent("test");

            pulsarProducer.sendMessage("topic-asd", myMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/stop")
    public void stopPulsarListener() {
        try {
            pulsarListenerManager.removeListeners();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/start")
    public void startPulsarListener() {
        try {
            pulsarListenerManager.addListeners();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}