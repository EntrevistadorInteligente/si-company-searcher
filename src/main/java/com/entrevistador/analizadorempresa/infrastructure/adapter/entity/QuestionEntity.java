package com.entrevistador.analizadorempresa.infrastructure.adapter.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "interviews")
@Data
public class QuestionEntity {

    @Field(name = "question_text", type = FieldType.Text)
    private String questionText;

    @Field(name = "question_title", type = FieldType.Text)
    private String questionTitle;

}

