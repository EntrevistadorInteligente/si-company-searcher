package com.entrevistador.analizadorempresa.infrastructure.rest.controller;

import com.entrevistador.analizadorempresa.application.usecases.AnalizadorEmpresa;
import com.entrevistador.analizadorempresa.domain.model.dto.AnalizadorEmpresaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/empresa")
@RequiredArgsConstructor
public class AnalizadorEmpresaController {
    private final AnalizadorEmpresa empresa;

    @PostMapping
    public Mono<ResponseEntity<?>> crear(@RequestBody AnalizadorEmpresaDto analizadorEmpresaDto) {
        return this.empresa.crear(analizadorEmpresaDto)
                .map(empresa -> ResponseEntity.status(HttpStatus.CREATED).body(empresa));
    }
}
