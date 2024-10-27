package com.entrevistador.analizadorempresa.infrastructure.adapter.mapper.out;

import com.entrevistador.analizadorempresa.domain.model.Entrevista;
import com.entrevistador.analizadorempresa.domain.model.Pregunta;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.EntrevistaEntity;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.PreguntaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.data.elasticsearch.core.SearchHit;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EntrevistaElasticsearchDaoMapper {
    // Map Out
    @Mapping(target = "id", source = "entrevistaEntity.id")
    @Mapping(target = "titulo", source = "entrevistaEntity.titulo")
    @Mapping(target = "puntuacion", source = "searchHit.score")
    @Mapping(target = "preguntas", source = "entrevistaEntity.preguntas", qualifiedByName = "mapOutPreguntas")
    Entrevista mapOutInterview(EntrevistaEntity entrevistaEntity, SearchHit<EntrevistaEntity> searchHit);

    @Named("mapOutPreguntas")
    default List<Pregunta> mapOutPreguntas(List<PreguntaEntity> preguntaEntities) {
        if (preguntaEntities == null) {
            return List.of();
        }
        return preguntaEntities.stream()
                .filter(Objects::nonNull)
                .map(this::mapOutPregunta)
                .toList();
    }

    Pregunta mapOutPregunta(PreguntaEntity preguntaEntity);
    // End Map Out
}
