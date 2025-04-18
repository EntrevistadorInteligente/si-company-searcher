package com.entrevistador.analizadorempresa.infrastructure.adapter.jms;

import com.entrevistador.analizadorempresa.domain.model.WhatsappMessage;
import com.entrevistador.analizadorempresa.domain.port.kafka.WhatsappPublisher;
import com.entrevistador.analizadorempresa.infrastructure.adapter.mapper.WhatsappMessageMapper;
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
public class WhatsappPublisherAdapter implements WhatsappPublisher {
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final WhatsappMessageMapper mapper;
    
    @Value("${kafka.topic.whatsapp.incoming}")
    private String whatsappIncomingTopic;
    
    @Override
    public Mono<Void> publishIncomingMessage(WhatsappMessage message) {
        log.info("Publicando mensaje en topic {}: messageId={}, senderId={}", 
                whatsappIncomingTopic, message.getMessageId(), message.getSenderId());
        
        return Mono.create(sink -> {
            try {
                String messageJson = mapper.toJsonString(message);
                CompletableFuture<SendResult<String, Object>> future = 
                        kafkaTemplate.send(whatsappIncomingTopic, message.getSenderId(), messageJson);
                
                future.whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Mensaje publicado en Kafka: messageId={}, offset={}", 
                                message.getMessageId(), result.getRecordMetadata().offset());
                        sink.success();
                    } else {
                        log.error("Error al publicar mensaje en Kafka: messageId={}, error={}", 
                                message.getMessageId(), ex.getMessage(), ex);
                        sink.error(ex);
                    }
                });
            } catch (Exception e) {
                log.error("Error al serializar mensaje para Kafka: {}", e.getMessage(), e);
                sink.error(e);
            }
        });
    }
} 