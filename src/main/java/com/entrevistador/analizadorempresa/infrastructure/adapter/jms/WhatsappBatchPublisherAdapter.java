package com.entrevistador.analizadorempresa.infrastructure.adapter.jms;

import com.entrevistador.analizadorempresa.domain.model.WhatsappBatch;
import com.entrevistador.analizadorempresa.domain.port.kafka.WhatsappBatchPublisher;
import com.entrevistador.analizadorempresa.infrastructure.adapter.mapper.WhatsappBatchMapper;
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
public class WhatsappBatchPublisherAdapter implements WhatsappBatchPublisher {
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final WhatsappBatchMapper mapper;
    
    @Value("${kafka.topic.whatsapp.batched}")
    private String whatsappBatchedTopic;
    
    @Override
    public Mono<Void> publishBatchedMessages(WhatsappBatch batch) {
        log.info("Publicando lote en topic {}: batchId={}, senderId={}, mensajes={}", 
                whatsappBatchedTopic, batch.getId(), batch.getSenderId(), batch.getMessages().size());
        
        return Mono.create(sink -> {
            try {
                String batchJson = mapper.toJsonString(batch);
                CompletableFuture<SendResult<String, Object>> future = 
                        kafkaTemplate.send(whatsappBatchedTopic, batch.getSenderId(), batchJson);
                
                future.whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Lote publicado en Kafka: batchId={}, offset={}", 
                                batch.getId(), result.getRecordMetadata().offset());
                        sink.success();
                    } else {
                        log.error("Error al publicar lote en Kafka: batchId={}, error={}", 
                                batch.getId(), ex.getMessage(), ex);
                        sink.error(ex);
                    }
                });
            } catch (Exception e) {
                log.error("Error al serializar lote para Kafka: {}", e.getMessage(), e);
                sink.error(e);
            }
        });
    }
} 