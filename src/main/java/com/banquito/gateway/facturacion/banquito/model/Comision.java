package com.banquito.gateway.facturacion.banquito.model;

import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.index.Indexed;
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
    private String id;

    @Field("cod_comision")
    @Indexed(unique = true)
    private String codComision;

    @Field("tipo")
    private String tipo;

    @Field("monto_base")
    private BigDecimal montoBase;

    @Field("transacciones_base")
    private Integer transaccionesBase;

    @Field("maneja_segmentos")
    private Boolean manejaSegmentos;
}