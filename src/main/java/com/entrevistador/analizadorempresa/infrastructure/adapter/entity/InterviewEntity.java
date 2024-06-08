package com.entrevistador.analizadorempresa.infrastructure.adapter.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "interviews")
@Data
public class InterviewEntity {

    @Id
    private String id;
    @Field(name = "company_name")
    private String companyName;
    private String title;
    @Field(name = "score", type = FieldType.Double)
    private double score;
    @Field(name = "interview_text")
    private String interviewText;
    @Field(type = FieldType.Nested)
    private List<QuestionEntity> questions;

}

