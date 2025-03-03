package com.banquito.gateway.facturacion.banquito.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.bson.types.ObjectId;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FacturacionComercioDTO {
    private ObjectId id;
    private String codFacturacionComercio;

    @NotBlank(message = "El código de comercio es requerido")
    @Size(max = 10, message = "El código de comercio no puede exceder los 10 caracteres")
    private String codComercio;

    @NotNull(message = "La fecha de inicio es requerida")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha fin es requerida")
    private LocalDate fechaFin;

    @NotNull(message = "El número de transacciones procesadas es requerido")
    @Min(value = 0, message = "El número de transacciones procesadas no puede ser negativo")
    private Integer transaccionesProcesadas;

    @NotBlank(message = "El código de comisión es requerido")
    @Size(max = 10, message = "El código de comisión no puede exceder los 10 caracteres")
    private String codComision;

    @NotNull(message = "El valor es requerido")
    @DecimalMin(value = "0.0", message = "El valor no puede ser negativo")
    @DecimalMax(value = "999999999.99", message = "El valor excede el límite permitido")
    private BigDecimal valor;

    @NotBlank(message = "El estado es requerido")
    @Pattern(regexp = "PEN|PAG|ANU", message = "El estado debe ser PEN, PAG o ANU")
    private String estado;

    private LocalDate fechaFacturacion;
    private LocalDate fechaPago;
}