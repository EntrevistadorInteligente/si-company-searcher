package com.entrevistador.analizadorempresa.infrastructure.adapter.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
@RequiredArgsConstructor
public class KafkaListenerAdapter {
    @KafkaListener(topics = "empty", groupId = "my-group")
    public void modificar1(String jsonData) {
    }

    @KafkaListener(topics = "empty", groupId = "my-group")
    public void modificar2(String jsonData) {
    }
}
