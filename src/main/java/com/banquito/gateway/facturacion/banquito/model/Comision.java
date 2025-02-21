package com.banquito.gateway.facturacion.banquito.model;

import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "comisiones")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Comision {
    @Id
    @EqualsAndHashCode.Include
    private String codComision;
    private String tipo;
    private BigDecimal montoBase;
    private Integer transaccionesBase;
    private Boolean manejaSegmentos;
}