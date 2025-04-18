package com.entrevistador.analizadorempresa.infrastructure.adapter.dao;

import com.entrevistador.analizadorempresa.domain.model.WhatsappMessage;
import com.entrevistador.analizadorempresa.domain.port.repository.WhatsappMessageDao;
import com.entrevistador.analizadorempresa.infrastructure.adapter.mapper.WhatsappMessageMapper;
import com.entrevistador.analizadorempresa.infrastructure.adapter.repository.WhatsappMessegeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Adaptador para el repositorio de mensajes de WhatsApp
 * Implementa el puerto del dominio y utiliza el DAO para acceder a la base de datos
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class WhatsappMessageBdDao implements WhatsappMessageDao {
    
    private final WhatsappMessegeRepository whatsappMessegeRepository;
    private final WhatsappMessageMapper mapper;


    @Override
    public Mono<WhatsappMessage> save(WhatsappMessage message) {
        return null;
    }

    @Override
    public Flux<WhatsappMessage> findBySenderIdOrderByReceivedAtDesc(String senderId) {
        return null;
    }

    @Override
    public Flux<WhatsappMessage> findByProcessedFalseOrderByReceivedAtAsc() {
        return null;
    }

    @Override
    public Mono<Long> countByProcessedFalse() {
        return null;
    }

    @Override
    public Flux<WhatsappMessage> findByReceivedAtBeforeAndProcessedFalse(LocalDateTime cutoffTime) {
        return null;
    }

    @Override
    public Mono<WhatsappMessage> findByMessageId(String messageId) {
        return null;
    }

    @Override
    public Mono<WhatsappMessage> findById(String id) {
        return null;
    }
}