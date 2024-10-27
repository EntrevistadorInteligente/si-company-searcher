package com.entrevistador.analizadorempresa.infrastructure.adapter.mapper.in;

import com.entrevistador.analizadorempresa.domain.valueobject.PosicionEntrevista;
import com.entrevistador.analizadorempresa.infrastructure.adapter.dto.in.KafkaPosicionEntrevistaInput;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface KafkaListenerMapper {
    // Map In
    PosicionEntrevista mapInPosicionEntrevista(KafkaPosicionEntrevistaInput kafkaPosicionEntrevistaInput);
    // End Map In
}
