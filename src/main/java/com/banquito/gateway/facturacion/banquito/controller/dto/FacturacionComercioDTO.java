package com.banquito.gateway.facturacion.banquito.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FacturacionComercioDTO {
    private String id;
    
    private String codFacturacionComercio;

    @NotBlank(message = "El código de comercio es requerido")
    private String codComercio;

    @NotNull(message = "La fecha de inicio es requerida")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha fin es requerida")
    private LocalDate fechaFin;

    @NotNull(message = "El número de transacciones procesadas es requerido")
    @Min(value = 0, message = "El número de transacciones procesadas no puede ser negativo")
    private Integer transaccionesProcesadas;

    @NotBlank(message = "El código de comisión es requerido")
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