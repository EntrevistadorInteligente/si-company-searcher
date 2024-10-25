package com.entrevistador.analizadorempresa.application.service;

import com.entrevistador.analizadorempresa.application.usecases.InvestigarEmpresa;
import com.entrevistador.analizadorempresa.domain.valueobject.PosicionEntrevista;
import com.entrevistador.analizadorempresa.domain.port.kafka.KafkaPublisher;
import com.entrevistador.analizadorempresa.domain.service.CrearInvestigarEmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class InvestigarEmpresaService implements InvestigarEmpresa {

    private final CrearInvestigarEmpresaService crearInvestigarEmpresaService;
    private final KafkaPublisher jmsPublisherAdapter;

    @Override
    public Mono<Void> ejecutar(PosicionEntrevista posicionEntrevista) {
        return this.crearInvestigarEmpresaService.create(posicionEntrevista)
                .flatMap(this.jmsPublisherAdapter::empresaListenerTopic);
    }

}