package com.entrevistador.analizadorempresa.infrastructure.adapter.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
@Builder
@Table(name = "informacion_empresa")
@AllArgsConstructor
public class InformacionEmpresaEntityPostgres {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String empresa;
    private String perfil;
    private String seniority;
    private String pais;
    private List<String> informacionEmpresaVect;
}
