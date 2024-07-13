package com.entrevistador.analizadorempresa.infrastructure.adapter.jms;

import com.entrevistador.analizadorempresa.application.service.InvestigarEmpresaService;
import com.entrevistador.analizadorempresa.infrastructure.adapter.dto.PosicionEntrevistaDto;
import com.entrevistador.analizadorempresa.infrastructure.adapter.mapper.AnalizadorEmpresaMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class JmsListenerAdapter {
    private final InvestigarEmpresaService investigarEmpresaService;
    private final ObjectMapper objectMapper;
    private final AnalizadorEmpresaMapper mapper;

    @KafkaListener(topics = "empresaPublisherTopic", groupId = "my-group")
    public void empresaPublisherTopic(String jsonData) {
        try {
            PosicionEntrevistaDto posicionEntrevista = this.objectMapper.readValue(jsonData, PosicionEntrevistaDto.class);
            Mono.just(this.mapper.mapInPosicionEntrevista(posicionEntrevista))
                    .flatMap(this.investigarEmpresaService::ejecutar)
                    .doOnSubscribe(subscription -> System.out.println("Starting processing: " + posicionEntrevista))
                    .doOnSuccess(success -> System.out.println("Processed successfully: " + posicionEntrevista))
                    .doOnError(error -> System.err.println("Error processing: " + error.getMessage()))
                    .subscribe();
        } catch (IOException e) {
            throw new RuntimeException("Error al deserializar el mensaje JSON", e);
        }
    }
}
