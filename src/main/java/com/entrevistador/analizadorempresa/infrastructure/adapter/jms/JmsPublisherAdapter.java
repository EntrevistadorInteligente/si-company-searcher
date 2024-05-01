package com.entrevistador.analizadorempresa.infrastructure.adapter.jms;

import com.entrevistador.analizadorempresa.domain.model.dto.MensajeAnalizadorEmpresaDto;
import com.entrevistador.analizadorempresa.domain.port.kafka.KafkaPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public final class JmsPublisherAdapter implements KafkaPublisher {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic-empresa-listener}")
    private String empresaListenerTopic;

    public Mono<Void> empresaListenerTopic(MensajeAnalizadorEmpresaDto mensajeAnalizadorEmpresaDto) {
        try {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(empresaListenerTopic, mensajeAnalizadorEmpresaDto);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Sent message=[" + mensajeAnalizadorEmpresaDto.getIdEntrevista() +
                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send message=[" +
                            mensajeAnalizadorEmpresaDto.toString() + "] due to : " + ex.getMessage());
                }
            });

        } catch (Exception ex) {
            System.out.println("ERROR : " + ex.getMessage());
        }

        return Mono.empty();
    }
}
