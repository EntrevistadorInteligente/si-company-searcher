package com.entrevistador.analizadorempresa.infrastructure.adapter.kafka;

import com.entrevistador.analizadorempresa.domain.port.kafka.KafkaPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public final class KafkaPublisherAdapter implements KafkaPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;
}
