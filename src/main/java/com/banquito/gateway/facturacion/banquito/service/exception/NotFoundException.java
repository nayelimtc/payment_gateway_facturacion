package com.banquito.gateway.facturacion.banquito.service.exception;

public class NotFoundException extends RuntimeException {
    private final String entity;
    private final String data;

    public NotFoundException(String entity, String data) {
        super();
        this.entity = entity;
        this.data = data;
    }

    @Override
    public String getMessage() {
        return "No se encontró " + this.entity + " con " + this.data;
    }
}