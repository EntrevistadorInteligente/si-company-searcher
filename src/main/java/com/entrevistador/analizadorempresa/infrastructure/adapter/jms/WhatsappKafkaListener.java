package com.entrevistador.analizadorempresa.infrastructure.adapter.jms;

import com.entrevistador.analizadorempresa.domain.model.WhatsappMessage;
import com.entrevistador.analizadorempresa.domain.port.repository.WhatsappMessageRepository;
import com.entrevistador.analizadorempresa.infrastructure.adapter.mapper.WhatsappMessageMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class WhatsappKafkaListener {

    private final WhatsappMessageRepository messageRepository;
    private final WhatsappMessageMapper messageMapper;
    
    /**
     * Consume mensajes del tópico whatsapp.incoming
     * Los mensajes se guardan en MongoDB para su posterior agrupación
     */
    @KafkaListener(
        topics = "${kafka.topic.whatsapp.incoming}", 
        groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeIncomingMessage(String jsonMessage) {
        log.info("Recibido mensaje en tópico whatsapp.incoming: {}", jsonMessage);
        try {
            // Convierte JSON a objeto de dominio
            WhatsappMessage message = messageMapper.fromJson(jsonMessage);
            
            // Guarda en MongoDB
            messageRepository.save(message)
                .doOnSuccess(savedMsg -> log.info("Mensaje guardado en MongoDB: messageId={}, senderId={}", 
                                           savedMsg.getMessageId(), savedMsg.getSenderId()))
                .doOnError(err -> log.error("Error al guardar mensaje en MongoDB: {}", err.getMessage(), err))
                .subscribe();
            
        } catch (JsonProcessingException e) {
            log.error("Error al deserializar mensaje JSON de whatsapp.incoming: {}", e.getMessage(), e);
        }
    }
}
