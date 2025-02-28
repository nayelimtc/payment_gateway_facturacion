package com.banquito.gateway.facturacion.banquito.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.index.Indexed;
import org.bson.types.ObjectId;
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
    private ObjectId id;

    @Field("cod_facturacion_comercio")
    private String codFacturacionComercio;

    @Field("cod_comercio")
    @Indexed
    private String codComercio;

    @Field("fecha_inicio")
    @Indexed
    private LocalDate fechaInicio;

    @Field("fecha_fin")
    private LocalDate fechaFin;

    @Field("transacciones_procesadas")
    private Integer transaccionesProcesadas;

    @Field("cod_comision")
    @Indexed
    private String codComision;

    @Field("valor")
    private BigDecimal valor;

    @Field("estado")
    @Indexed
    private String estado;

    @Field("fecha_facturacion")
    private LocalDate fechaFacturacion;

    @Field("fecha_pago")
    private LocalDate fechaPago;

    public void prePersist() {
        if (this.id == null) {
            this.id = new ObjectId();
        }
        if (this.codFacturacionComercio == null) {
            this.codFacturacionComercio = this.id.toString();
        }
    }
}