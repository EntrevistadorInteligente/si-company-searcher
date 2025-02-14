package com.entrevistador.analizadorempresa.infrastructure.adapter.dao;

import com.entrevistador.analizadorempresa.domain.model.InformacionEmpresa;
import com.entrevistador.analizadorempresa.domain.model.Interview;
import com.entrevistador.analizadorempresa.domain.port.EntrevistaElasticsearch;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.EntrevistaEntity;
import com.entrevistador.analizadorempresa.infrastructure.adapter.io.LoadResource;
import com.entrevistador.analizadorempresa.infrastructure.adapter.mapper.AnalizadorEmpresaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class EntrevistaElasticsearchDao implements EntrevistaElasticsearch {
    private final ReactiveElasticsearchOperations operations;
    private final AnalizadorEmpresaMapper mapper;
    private final LoadResource loadResource;

    @Override
    public Flux<Interview> obtenerEntrevistasPorRepo(InformacionEmpresa informacionEmpresa) {

        Pageable pageable = PageRequest.of(0, 10);
        Flux<Interview> resultadosConEmpresa = buscarConEmpresa(informacionEmpresa, pageable);

        return resultadosConEmpresa.switchIfEmpty(buscarSinEmpresa(informacionEmpresa, pageable));
    }

    private Flux<Interview> buscarConEmpresa(InformacionEmpresa informacionEmpresa, Pageable pageable) {

        String queryTemplate = this.loadResource.loadQueryTemplateWithCompany();
        String queryString = replacePlaceholders(queryTemplate, informacionEmpresa, true);

        StringQuery stringQuery = new StringQuery("""
                %s
                """.formatted(queryString), pageable);

        return operations.search(stringQuery, EntrevistaEntity.class)
                .map(this::mapToInterview);
    }

    private Flux<Interview> buscarSinEmpresa(InformacionEmpresa informacionEmpresa, Pageable pageable) {
        String queryTemplate = this.loadResource.loadQueryTemplateWithoutCompany();
        String queryString = replacePlaceholders(queryTemplate, informacionEmpresa, false);

        StringQuery stringQuery = new StringQuery("""
                %s
                """.formatted(queryString), pageable);

        return operations.search(stringQuery, EntrevistaEntity.class)
                .map(this::mapToInterview);
    }

    private String cleanValue(String value) {
        return value.replaceAll("[\\r\\n]+", " ").trim();
    }

    private String replacePlaceholders(String queryTemplate, InformacionEmpresa informacionEmpresa, boolean withCompany) {
        if (withCompany) {
            return queryTemplate
                    .replace("{COMPANY_NAME}", cleanValue(informacionEmpresa.getEmpresa()))
                    .replace("{COMPANY_NAME_LOWERCASE}", cleanValue(informacionEmpresa.getEmpresa().toLowerCase()))
                    .replace("{PROFILE_SENIORITY}", cleanValue(informacionEmpresa.getPerfil() + " " + informacionEmpresa.getSeniority()))
                    .replace("{DESCRIPTION_VACANCY}", cleanValue(informacionEmpresa.getDescripcionVacante()));
        } else {
            return queryTemplate
                    .replace("{PROFILE_SENIORITY}", cleanValue(informacionEmpresa.getPerfil() + " " + informacionEmpresa.getSeniority()))
                    .replace("{DESCRIPTION_VACANCY}", cleanValue(informacionEmpresa.getDescripcionVacante()));
        }
    }

    private Interview mapToInterview(SearchHit<EntrevistaEntity> entrevistaEntitySearchHit) {
        EntrevistaEntity entity = entrevistaEntitySearchHit.getContent();
        return this.mapper.mapOutInterview(entity, entrevistaEntitySearchHit);
    }
}
