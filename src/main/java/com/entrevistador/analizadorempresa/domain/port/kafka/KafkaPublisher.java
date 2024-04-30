package com.entrevistador.analizadorempresa.domain.port.kafka;

import com.entrevistador.analizadorempresa.domain.model.dto.MensajeAnalizadorEmpresaDto;
import reactor.core.publisher.Mono;

public interface KafkaPublisher {
    Mono<Void> empresaListenerTopic(MensajeAnalizadorEmpresaDto mensajeAnalizadorEmpresaDto);
}
