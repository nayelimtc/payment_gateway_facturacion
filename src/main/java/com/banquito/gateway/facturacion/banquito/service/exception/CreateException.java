package com.banquito.gateway.facturacion.banquito.service.exception;

public class CreateException extends RuntimeException {
    private final String entity;
    private final String message;

    public CreateException(String entity, String message) {
        super();
        this.entity = entity;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "Error al procesar " + this.entity + ": " + this.message;
    }
}