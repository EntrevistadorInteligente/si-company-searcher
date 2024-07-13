package com.entrevistador.analizadorempresa.domain.service;

import com.entrevistador.analizadorempresa.domain.model.InformacionEmpresa;
import com.entrevistador.analizadorempresa.domain.model.Interview;
import com.entrevistador.analizadorempresa.domain.model.MensajeAnalizadorEmpresa;
import com.entrevistador.analizadorempresa.domain.model.PosicionEntrevista;
import com.entrevistador.analizadorempresa.domain.model.ProcesoEntrevista;
import com.entrevistador.analizadorempresa.domain.model.enums.EstadoEntrevistaEnum;
import com.entrevistador.analizadorempresa.domain.port.EntrevistaElasticsearch;
import com.entrevistador.analizadorempresa.domain.port.InformacionEmpresaDao;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class CrearInvestigarEmpresaService {
    private static final String ANALIZADOR_EMPRESA = "ANALIZADOR_EMPRESA";

    private final InformacionEmpresaDao informacionEmpresaDao;
    private final EntrevistaElasticsearch entrevistaElasticsearch;

    public Mono<MensajeAnalizadorEmpresa> create(PosicionEntrevista posicionEntrevista) {
        return this.entrevistaElasticsearch.obtenerEntrevistasPorRepo(posicionEntrevista.getFormulario())
                .collectList()
                .flatMapMany(this::filtrarEntrevistasPorThreshold)
                .collectList()
                .flatMap(entrevistasFiltradas ->
                        this.informacionEmpresaDao.create(posicionEntrevista.getFormulario(), entrevistasFiltradas)
                                .zipWith(Mono.just(posicionEntrevista), this::crearMensajeAnalizadorEmpresa)
                );
    }

    private Flux<Interview> filtrarEntrevistasPorThreshold(List<Interview> entrevistas) {
        if (entrevistas.isEmpty()) {
            return Flux.empty();
        }

        double highestScore = entrevistas.stream()
                .mapToDouble(Interview::getPuntuacion)
                .max()
                .orElse(0);
        double threshold = highestScore * 0.5;

        return Flux.fromIterable(entrevistas)
                .filter(entrevista -> entrevista.getPuntuacion() >= threshold);
    }

    private MensajeAnalizadorEmpresa crearMensajeAnalizadorEmpresa(InformacionEmpresa informacionEmpresa,
                                                                   PosicionEntrevista posicionEntrevista) {
        ProcesoEntrevista procesoEntrevista = this.crearProcesoEntrevista(posicionEntrevista);
        return MensajeAnalizadorEmpresa.builder()
                .procesoEntrevista(procesoEntrevista)
                .idEntrevista(posicionEntrevista.getIdEntrevista())
                .idInformacionEmpresaRag(informacionEmpresa.getIdInformacionEmpresaRag())
                .build();
    }

    private ProcesoEntrevista crearProcesoEntrevista(PosicionEntrevista posicionEntrevista) {
        return ProcesoEntrevista.builder()
                .uuid(posicionEntrevista.getEventoEntrevistaId())
                .estado(EstadoEntrevistaEnum.FN)
                .fuente(ANALIZADOR_EMPRESA)
                .build();
    }
}
