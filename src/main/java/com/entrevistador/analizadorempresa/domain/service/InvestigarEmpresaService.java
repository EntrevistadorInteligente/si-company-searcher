package com.entrevistador.analizadorempresa.domain.service;

import com.entrevistador.analizadorempresa.domain.model.WhatsappMessage;
import com.entrevistador.analizadorempresa.domain.port.kafka.WhatsappPublisher;
import com.entrevistador.analizadorempresa.domain.port.repository.WhatsappMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvestigarEmpresaService {
    
    private final WhatsappMessageRepository whatsappMessageRepository;
    private final WhatsappPublisher whatsappPublisher;
    
    /**
     * Procesa un mensaje entrante de WhatsApp
     * - Guarda el mensaje en la base de datos
     * - Publica el mensaje en Kafka para su procesamiento asíncrono
     * - Marca el mensaje como procesado
     * 
     * @param whatsappMessage Mensaje de WhatsApp a procesar
     * @return Mono que completa cuando el mensaje ha sido procesado
     */
    public Mono<WhatsappMessage> processIncomingMessage(WhatsappMessage whatsappMessage) {
        log.info("Procesando mensaje entrante de WhatsApp: messageId={}, senderId={}", 
                whatsappMessage.getMessageId(), whatsappMessage.getSenderId());
        
        // Primero guardamos el mensaje en MongoDB para persistencia/resiliencia
        return whatsappMessageRepository.save(whatsappMessage)
                // Luego publicamos el mensaje en Kafka
                .flatMap(savedMessage -> whatsappPublisher.publishIncomingMessage(savedMessage)
                        // Cuando la publicación es exitosa, actualizamos el mensaje como procesado
                        .then(Mono.defer(() -> {
                            WhatsappMessage processedMessage = WhatsappMessage.builder()
                                    .id(savedMessage.getId())
                                    .messageId(savedMessage.getMessageId())
                                    .senderId(savedMessage.getSenderId())
                                    .textContent(savedMessage.getTextContent())
                                    .rawPayload(savedMessage.getRawPayload())
                                    .receivedAt(savedMessage.getReceivedAt())
                                    .processed(true)
                                    .processingStatus("PROCESSED")
                                    .processedAt(LocalDateTime.now())
                                    .build();
                            
                            return whatsappMessageRepository.save(processedMessage);
                        })))
                .doOnSuccess(result -> log.info("Mensaje de WhatsApp procesado correctamente: messageId={}", whatsappMessage.getMessageId()))
                .doOnError(error -> log.error("Error al procesar mensaje de WhatsApp: messageId={}, error={}", 
                        whatsappMessage.getMessageId(), error.getMessage(), error));
    }
    
    /**
     * Reprocesa los mensajes pendientes
     * Útil para mensajes que no pudieron ser publicados en Kafka
     * 
     * @return Mono que completa cuando todos los mensajes pendientes han sido reprocesados
     */
    public Mono<Void> reprocessPendingMessages() {
        log.info("Reprocesando mensajes pendientes");
        
        return whatsappMessageRepository.findByProcessedFalseOrderByReceivedAtAsc()
                .flatMap(this::processIncomingMessage)
                .then()
                .doOnSuccess(result -> log.info("Reprocesamiento de mensajes pendientes completado"));
    }
} 