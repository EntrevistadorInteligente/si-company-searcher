package com.entrevistador.analizadorempresa.infrastructure.adapter.entity;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "interview")
public class InterviewEntity {

    private String id;
}
