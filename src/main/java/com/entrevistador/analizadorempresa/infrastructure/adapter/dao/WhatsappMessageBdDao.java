package com.entrevistador.analizadorempresa.infrastructure.adapter.dao;

import com.entrevistador.analizadorempresa.domain.model.WhatsappMessage;
import com.entrevistador.analizadorempresa.domain.port.repository.WhatsappMessageDao;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.WhatsappMessageEntity;
import com.entrevistador.analizadorempresa.infrastructure.adapter.mapper.WhatsappMessageMapper;
import com.entrevistador.analizadorempresa.infrastructure.adapter.repository.WhatsappMessegeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementación del puerto WhatsappMessageDao usando MongoDB
 * Esta clase es la implementación del adaptador que conecta el dominio con la infraestructura
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class WhatsappMessageBdDao implements WhatsappMessageDao {
    
    private final WhatsappMessegeRepository whatsappRepository;
    private final WhatsappMessageMapper mapper;

    @Override
    public Mono<WhatsappMessage> save(WhatsappMessage message) {
        log.debug("Guardando mensaje: ID={}, sender={}", message.getMessageId(), message.getSenderId());
        
        return Mono.just(message)
                .map(mapper::toEntity)
                .flatMap(whatsappRepository::save)
                .map(mapper::toDomain)
                .doOnSuccess(saved -> log.debug("Mensaje guardado exitosamente: ID={}", saved.getMessageId()))
                .doOnError(e -> log.error("Error al guardar mensaje: ID={}, error={}", 
                        message.getMessageId(), e.getMessage()));
    }

    @Override
    public Flux<WhatsappMessage> findByProcessedFalseOrderByReceivedAtAsc() {
        log.debug("Buscando mensajes no procesados");
        
        return whatsappRepository.findByProcessedFalseOrderByReceivedAtAsc()
                .map(mapper::toDomain)
                .doOnComplete(() -> log.debug("Búsqueda de mensajes no procesados completada"));
    }

    @Override
    public Mono<WhatsappMessage> findById(String id) {
        log.debug("Buscando mensaje por id: {}", id);
        
        return whatsappRepository.findById(id)
                .map(mapper::toDomain)
                .doOnSuccess(message -> {
                    if (message != null) {
                        log.debug("Mensaje encontrado: ID={}", id);
                    } else {
                        log.debug("Mensaje no encontrado: ID={}", id);
                    }
                });
    }
}