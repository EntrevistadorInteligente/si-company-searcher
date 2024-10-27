package com.entrevistador.analizadorempresa.domain.port.kafka;

import com.entrevistador.analizadorempresa.domain.valueobject.MensajeAnalizadorEmpresa;
import reactor.core.publisher.Mono;

public interface KafkaPublisher {
    Mono<Void> empresaListenerTopic(MensajeAnalizadorEmpresa mensajeAnalizadorEmpresa);
}
