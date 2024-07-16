package com.entrevistador.analizadorempresa.application.service;

import com.entrevistador.analizadorempresa.application.usecases.InvestigarEmpresa;
import com.entrevistador.analizadorempresa.domain.model.PosicionEntrevista;
import com.entrevistador.analizadorempresa.domain.service.CrearInvestigarEmpresaService;
import com.entrevistador.analizadorempresa.infrastructure.adapter.jms.JmsPublisherAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class InvestigarEmpresaService implements InvestigarEmpresa {

    private final CrearInvestigarEmpresaService crearInvestigarEmpresaService;
    private final JmsPublisherAdapter jmsPublisherAdapter;

    @Override
    public Mono<Void> ejecutar(PosicionEntrevista posicionEntrevista) {
        return this.crearInvestigarEmpresaService.create(posicionEntrevista)
                .flatMap(this.jmsPublisherAdapter::empresaListenerTopic);
    }

}