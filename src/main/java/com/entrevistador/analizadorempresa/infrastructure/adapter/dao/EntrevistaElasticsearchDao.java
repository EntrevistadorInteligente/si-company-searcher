package com.entrevistador.analizadorempresa.infrastructure.adapter.dao;

import com.entrevistador.analizadorempresa.domain.exception.QueryFileException;
import com.entrevistador.analizadorempresa.domain.model.dto.InformacionEmpresaDto;
import com.entrevistador.analizadorempresa.domain.model.dto.InterviewDto;
import com.entrevistador.analizadorempresa.domain.model.dto.QuestionDto;
import com.entrevistador.analizadorempresa.domain.port.EntrevistaElasticsearch;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.EntrevistaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntrevistaElasticsearchDao implements EntrevistaElasticsearch {

    @Value("classpath:elasticsearchqueries/query-with-company.json")
    private Resource queryWithCompanyResource;

    @Value("classpath:elasticsearchqueries/query-without-company.json")
    private Resource queryWithoutCompanyResource;

    private final ReactiveElasticsearchOperations operations;

    @Override
    public Flux<InterviewDto> obtenerEntrevistasPorRepo(InformacionEmpresaDto analizadorEmpresaDto) {

        Pageable pageable = PageRequest.of(0, 10);
        Flux<InterviewDto> resultadosConEmpresa = buscarConEmpresa(analizadorEmpresaDto, pageable);

        return resultadosConEmpresa.switchIfEmpty(buscarSinEmpresa(analizadorEmpresaDto, pageable));
    }

    private Flux<InterviewDto> buscarConEmpresa(InformacionEmpresaDto analizadorEmpresaDto, Pageable pageable) {

        String queryTemplate = loadQueryTemplate(queryWithCompanyResource);
        String queryString = replacePlaceholders(queryTemplate, analizadorEmpresaDto, true);

        StringQuery stringQuery = new StringQuery("""
            %s
            """.formatted(queryString), pageable);

        return operations.search(stringQuery, EntrevistaEntity.class)
                .map(this::mapToInterviewDto);
    }

    private Flux<InterviewDto> buscarSinEmpresa(InformacionEmpresaDto analizadorEmpresaDto, Pageable pageable) {
        String queryTemplate = loadQueryTemplate(queryWithoutCompanyResource);
        String queryString = replacePlaceholders(queryTemplate, analizadorEmpresaDto, false);

        StringQuery stringQuery = new StringQuery("""
            %s
            """.formatted(queryString), pageable);

        return operations.search(stringQuery, EntrevistaEntity.class)
                .map(this::mapToInterviewDto);
    }


    private String loadQueryTemplate(Resource resource) {
        try (InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new QueryFileException("Failed to load query template", e);
        }
    }

    private String cleanValue(String value) {
        return value.replaceAll("[\\r\\n]+", " ").trim();
    }

    private String replacePlaceholders(String queryTemplate, InformacionEmpresaDto dto, boolean withCompany) {
        if (withCompany) {
            return queryTemplate
                    .replace("{COMPANY_NAME}", cleanValue(dto.getEmpresa()))
                    .replace("{COMPANY_NAME_LOWERCASE}", cleanValue(dto.getEmpresa().toLowerCase()))
                    .replace("{PROFILE_SENIORITY}", cleanValue(dto.getPerfil() + " " + dto.getSeniority()))
                    .replace("{DESCRIPTION_VACANCY}", cleanValue(dto.getDescripcionVacante()));
        } else {
            return queryTemplate
                    .replace("{PROFILE_SENIORITY}", cleanValue(dto.getPerfil() + " " + dto.getSeniority()))
                    .replace("{DESCRIPTION_VACANCY}", cleanValue(dto.getDescripcionVacante()));
        }
    }

    private InterviewDto mapToInterviewDto(SearchHit<EntrevistaEntity> hit) {
        EntrevistaEntity entity = hit.getContent();
        List<QuestionDto> preguntas = new ArrayList<>();
        if (entity != null && entity.getPreguntas() != null) {
            preguntas = entity.getPreguntas().stream()
                    .map(pregunta -> QuestionDto.builder()
                            .titulo(pregunta.getTitulo())
                            .descripcion(pregunta.getDescripcion())
                            .build())
                    .toList();
        }
        return InterviewDto.builder()
                .nombreEmpresa(entity.getNombreEmpresa())
                .tituloOferta(entity.getTitulo())
                .detalleAdicional(entity.getDetalleAdicional())
                .puntuacion(hit.getScore())
                .preguntas(preguntas)
                .detallesExperiencia(entity.getDetallesExperiencia())
                .build();
    }
}
