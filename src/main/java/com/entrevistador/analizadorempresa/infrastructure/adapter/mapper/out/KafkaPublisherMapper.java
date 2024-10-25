package com.entrevistador.analizadorempresa.infrastructure.adapter.mapper.out;

import com.entrevistador.analizadorempresa.domain.valueobject.MensajeAnalizadorEmpresa;
import com.entrevistador.analizadorempresa.infrastructure.adapter.dto.out.KafkaMensajeAnalizadorOutput;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface KafkaPublisherMapper {
    // Map Out
    KafkaMensajeAnalizadorOutput mapOutKafkaMensajeAnalizadorOutput(MensajeAnalizadorEmpresa mensajeAnalizadorEmpresa);
    // End Map Out
}
