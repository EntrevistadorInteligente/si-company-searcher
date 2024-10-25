package com.entrevistador.analizadorempresa.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 * Clase que representa el dominio de InformacionEmpresaEntity
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformacionEmpresa {
    private String idInformacionEmpresaRag;
    private String descripcionVacante;
    private String empresa;
    private String perfil;
    private String seniority;
    private String pais;
}
