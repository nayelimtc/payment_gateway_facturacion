package com.banquito.gateway.facturacion.banquito.model;

import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.bson.types.ObjectId;
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
    private ObjectId id;

    @Field("cod_comision")
    private String codComision;

    @Field("tipo")
    private String tipo;

    @Field("monto_base")
    private BigDecimal montoBase;

    @Field("transacciones_base")
    private Integer transaccionesBase;

    @Field("maneja_segmentos")
    private Boolean manejaSegmentos;

    public void prePersist() {
        if (this.id == null) {
            this.id = new ObjectId();
        }
        if (this.codComision == null) {
            this.codComision = this.id.toString();
        }
    }
}