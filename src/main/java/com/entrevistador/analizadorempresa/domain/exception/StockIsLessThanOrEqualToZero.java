package com.entrevistador.analizadorempresa.domain.exception;

public class StockIsLessThanOrEqualToZero extends RuntimeException {
    public StockIsLessThanOrEqualToZero() {
        super("The stock is less than or equal to zero");
    }
}
