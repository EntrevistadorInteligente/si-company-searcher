package com.entrevistador.analizadorempresa.infrastructure.adapter;


import com.entrevistador.analizadorempresa.domain.model.dto.InformacionEmpresaDto;
import com.entrevistador.analizadorempresa.domain.port.EntrevistaElasticsearch;
import com.entrevistador.analizadorempresa.infrastructure.adapter.entity.InterviewEntity;
import com.entrevistador.analizadorempresa.infrastructure.adapter.repository.InterviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/empresa")
@RequiredArgsConstructor
public class VistaPreviaEntrevistaController {

    private final EntrevistaElasticsearch entrevistaPrueba;

    private final InterviewRepository entrevistaPrueba2;

    @PostMapping()
    public Flux<InterviewEntity> mostrarListaPerfiles(@RequestBody InformacionEmpresaDto analizadorEmpresaDto){
        var asd = entrevistaPrueba.obtenerEntrevistas(analizadorEmpresaDto );

        return asd;
    }

    @PostMapping("/2")
    public Mono<InterviewEntity> mostrarListaPerfiles2(@RequestBody InformacionEmpresaDto analizadorEmpresaDto){
        var asd = entrevistaPrueba2.findById("49Tl8I8Bkeccm24tEmpw" );

        return asd;
    }


}

