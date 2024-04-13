package com.entrevistador.analizadorempresa.infrastructure.adapter.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "empresa")
@AllArgsConstructor
public class AnalizadorEmpresaEntity {
    @Id
    private String uuid;
}
