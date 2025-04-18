package com.entrevistador.analizadorempresa.infrastructure.adapter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.data.elasticsearch.core.SearchHit;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnalizadorEmpresaMapper {
    PosicionEntrevista mapInPosicionEntrevista(PosicionEntrevistaDto posicionEntrevistaDto);

    @Mapping(target = "informacionEmpresaVect", source = "interviews")
    InformacionEmpresaEntity mapInInformacionEmpresaEntity(InformacionEmpresa informacionEmpresa,
                                                           List<Interview> interviews);

    @Mapping(target = "titulo", source = "tituloOferta")
    @Mapping(target = "preguntas", source = "preguntas")
    EntrevistaEntity mapInEntrevistaEntity(Interview interview);

    PreguntaEntity mapInPreguntaEntity(Question question);

    MensajeAnalizadorEmpresaDto mapOutMensajeAnalizadorEmpresa(MensajeAnalizadorEmpresa mensajeAnalizadorEmpresa);

    @Mapping(target = "idInformacionEmpresaRag", source = "uuid")
    @Mapping(target = "descripcionVacante", ignore = true)
    InformacionEmpresa mapOutInformacionEmpresa(InformacionEmpresaEntity informacionEmpresaEntity);

    @Mapping(target = "tituloOferta", source = "entrevistaEntity.titulo")
    @Mapping(target = "puntuacion", source = "searchHit.score")
    @Mapping(target = "preguntas", source = "entrevistaEntity.preguntas", qualifiedByName = "mapOutQuestions")
    Interview mapOutInterview(EntrevistaEntity entrevistaEntity, SearchHit<EntrevistaEntity> searchHit);

    @Named("mapOutQuestions")
    default List<Question> mapOutQuestions(List<PreguntaEntity> preguntaEntities) {
        if  (preguntaEntities == null) {
            return List.of();
        }
        return preguntaEntities.stream()
                .filter(Objects::nonNull)
                .map(this::mapOutQuestion)
                .toList();
    }

    Question mapOutQuestion(PreguntaEntity preguntaEntity);
}
