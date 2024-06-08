package com.entrevistador.analizadorempresa.infrastructure.adapter.dao;

import com.entrevistador.analizadorempresa.domain.model.dto.InformacionEmpresaDto;
import com.entrevistador.analizadorempresa.domain.port.EntrevistaElasticsearch;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.InterviewEntity;
import com.entrevistador.analizadorempresa.infrastructure.adapter.repository.InterviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class EntrevistaElasticsearchDao implements EntrevistaElasticsearch {

    private final InterviewRepository entrevistaPrueba;

    @Override
    public Flux<InterviewEntity> obtenerEntrevistas(InformacionEmpresaDto analizadorEmpresaDto) {
        List<Map<String, String>> queries = List.of(
                Map.of("query", analizadorEmpresaDto.getPerfil())
        );
        String multiMatchQueries = queries.stream()
                .map(query -> String.format("""
                {
                    "multi_match": {
                        "query": "%s",
                        "fields": [
                            "title^3",
                            "interview_text^2"
                        ]
                    }
                }
                """, query.get("query")))
                .collect(Collectors.joining(","));

        return entrevistaPrueba.findByCompanyName(analizadorEmpresaDto.getEmpresa(),multiMatchQueries);
    }
}
