package com.entrevistador.analizadorempresa.infrastructure.adapter.jms;

import com.entrevistador.analizadorempresa.application.service.InvestigarEmpresaService;
import com.entrevistador.analizadorempresa.domain.model.dto.PosicionEntrevistaDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class JmsListenerAdapter {
    private final InvestigarEmpresaService investigarEmpresaService;

    @KafkaListener(topics = "empresaPublisherTopic", groupId = "my-group")
    public void empresaPublisherTopic(String jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            PosicionEntrevistaDto posicionEntrevista = objectMapper.readValue(jsonData, PosicionEntrevistaDto.class);
            Mono.just(posicionEntrevista)
                    .flatMap(this.investigarEmpresaService::ejecutar)
                    .block();
        } catch (IOException e) {
            throw new RuntimeException("Error al deserializar el mensaje JSON", e);
        }
    }
}
