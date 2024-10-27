package com.entrevistador.analizadorempresa.infrastructure.adapter.mapper.out;

import com.entrevistador.analizadorempresa.domain.model.Entrevista;
import com.entrevistador.analizadorempresa.domain.model.InformacionEmpresa;
import com.entrevistador.analizadorempresa.domain.model.Pregunta;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.EntrevistaEntity;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.InformacionEmpresaEntity;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.PreguntaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InformacionEmpresaBdDaoMapper {

    // Map In
    @Mapping(target = "informacionEmpresaVect", source = "entrevistas")
    InformacionEmpresaEntity mapInInformacionEmpresaEntity(InformacionEmpresa informacionEmpresa,
                                                           List<Entrevista> entrevistas);

    @Mapping(target = "preguntas", source = "preguntas")
    EntrevistaEntity mapInEntrevistaEntity(Entrevista entrevista);

    PreguntaEntity mapInPreguntaEntity(Pregunta pregunta);
    // End Map In

    // Map Out
    @Mapping(target = "idInformacionEmpresaRag", source = "uuid")
    @Mapping(target = "descripcionVacante", ignore = true)
    InformacionEmpresa mapOutInformacionEmpresa(InformacionEmpresaEntity informacionEmpresaEntity);
    // End Map Out
}
