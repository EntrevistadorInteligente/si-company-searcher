package com.entrevistador.analizadorempresa.infrastructure.adapter.mapper.in;

import com.entrevistador.analizadorempresa.domain.valueobject.PosicionEntrevista;
import com.entrevistador.analizadorempresa.infrastructure.adapter.dto.in.KafkaPosicionEntrevistaInput;
import com.entrevistador.analizadorempresa.utils.AnalizadorEmpresaMock;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class KafkaListenerMapperTest {
    private final KafkaListenerMapper mapper = Mappers.getMapper(KafkaListenerMapper.class);

    @Test
    void testMapInPosicionEntrevista() throws IOException {
        KafkaPosicionEntrevistaInput kafkaPosicionEntrevistaInput = AnalizadorEmpresaMock.getInstance().getPosicionEntrevistaDto();
        PosicionEntrevista posicionEntrevista = this.mapper.mapInPosicionEntrevista(kafkaPosicionEntrevistaInput);

        assertNotNull(posicionEntrevista);
        assertNotNull(posicionEntrevista.getIdEntrevista());
        assertNotNull(posicionEntrevista.getEventoEntrevistaId());
        assertNotNull(posicionEntrevista.getFormulario());
        assertNotNull(posicionEntrevista.getFormulario().getIdInformacionEmpresaRag());
        assertNotNull(posicionEntrevista.getFormulario().getDescripcionVacante());
        assertNotNull(posicionEntrevista.getFormulario().getEmpresa());
        assertNotNull(posicionEntrevista.getFormulario().getPerfil());
        assertNotNull(posicionEntrevista.getFormulario().getSeniority());
        assertNotNull(posicionEntrevista.getFormulario().getPais());
    }

}