package com.banquito.gateway.facturacion.banquito.controller.dto;

import java.math.BigDecimal;

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
public class ComisionDTO {
    private String id;
    
    @Size(max = 10, message = "El código de comisión no puede exceder los 10 caracteres")
    private String codComision;

    @NotBlank(message = "El tipo es requerido")
    @Pattern(regexp = "POR|FIJ", message = "El tipo debe ser POR o FIJ")
    @Size(max = 3, message = "El tipo no puede exceder los 3 caracteres")
    private String tipo;

    @NotNull(message = "El monto base es requerido")
    @DecimalMin(value = "0.0", message = "El monto base no puede ser negativo")
    @DecimalMax(value = "99999999999999999999.99", message = "El monto base excede el límite permitido")
    private BigDecimal montoBase;

    @NotNull(message = "El número de transacciones base es requerido")
    @Min(value = 0, message = "El número de transacciones base no puede ser negativo")
    @DecimalMax(value = "999999999", message = "El número de transacciones base excede el límite permitido")
    private Integer transaccionesBase;
}