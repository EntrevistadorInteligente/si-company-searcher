package com.entrevistador.analizadorempresa.domain.exception;

public class PriceIsLessThanOrEqualToZero extends RuntimeException {
    public PriceIsLessThanOrEqualToZero() {
        super("The price is less than or equal to zero");
    }
}
