package com.banquito.gateway.facturacion.banquito.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "facturaciones_comercio")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FacturacionComercio {
    @Id
    @EqualsAndHashCode.Include
    private String codFacturacionComercio;
    private String codComercio;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer transaccionesProcesadas;
    private String codComision;
    private BigDecimal valor;
    private String estado;
    private LocalDate fechaFacturacion;
    private LocalDate fechaPago;
}