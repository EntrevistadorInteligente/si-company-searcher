package com.entrevistador.analizadorempresa.domain.model.dto;

import lombok.Builder;

@Builder
public class PerfilEntrevistaDto {
    private String empresa;
    private String perfil;
    private String seniority;
    private String pais;
}
