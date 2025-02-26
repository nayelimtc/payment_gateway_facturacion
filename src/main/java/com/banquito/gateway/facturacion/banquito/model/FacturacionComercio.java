package com.banquito.gateway.facturacion.banquito.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.index.Indexed;
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
    private String id;
    
    @Field("cod_facturacion_comercio")
    @Indexed(unique = true)
    private String codFacturacionComercio;
    
    @Field("cod_comercio")
    private String codComercio;
    
    @Field("fecha_inicio")
    private LocalDate fechaInicio;
    
    @Field("fecha_fin")
    private LocalDate fechaFin;
    
    @Field("transacciones_procesadas")
    private Integer transaccionesProcesadas;
    
    @Field("cod_comision")
    private String codComision;
    
    @Field("valor")
    private BigDecimal valor;
    
    @Field("estado")
    private String estado;
    
    @Field("fecha_facturacion")
    private LocalDate fechaFacturacion;
    
    @Field("fecha_pago")
    private LocalDate fechaPago;
}