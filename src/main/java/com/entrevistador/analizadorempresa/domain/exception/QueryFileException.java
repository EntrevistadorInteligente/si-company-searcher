package com.entrevistador.analizadorempresa.domain.exception;

public class QueryFileException extends RuntimeException{

    public QueryFileException(String message, Throwable e){
        super(message, e);
    }
}
