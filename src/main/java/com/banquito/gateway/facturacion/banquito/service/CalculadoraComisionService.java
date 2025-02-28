package com.banquito.gateway.facturacion.banquito.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.banquito.gateway.facturacion.banquito.model.Comision;
import com.banquito.gateway.facturacion.banquito.service.exception.CreateException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculadoraComisionService {
    
    private final ComisionService comisionService;

    public BigDecimal calcularComision(String codComision, Integer transacciones, BigDecimal montoTotal) {
        log.debug("Calculando comisión para código: {}, transacciones: {}, monto: {}", 
            codComision, transacciones, montoTotal);

        Comision comision = this.comisionService.findByCodComision(codComision);
        
        if (transacciones < comision.getTransaccionesBase()) {
            return comision.getMontoBase();
        }

        if ("POR".equals(comision.getTipo())) {
            return calcularComisionPorcentual(montoTotal, comision.getMontoBase());
        } else if ("FIJ".equals(comision.getTipo())) {
            return calcularComisionFija(transacciones, comision.getMontoBase(), comision.getTransaccionesBase());
        } else {
            throw new CreateException("Comisión", "Tipo de comisión no válido: " + comision.getTipo());
        }
    }

    private BigDecimal calcularComisionPorcentual(BigDecimal montoTotal, BigDecimal porcentaje) {
        return montoTotal.multiply(porcentaje.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP))
            .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularComisionFija(Integer transacciones, BigDecimal montoBase, Integer transaccionesBase) {
        BigDecimal factorExceso = new BigDecimal(transacciones)
            .divide(new BigDecimal(transaccionesBase), 0, RoundingMode.UP);
        return montoBase.multiply(factorExceso).setScale(2, RoundingMode.HALF_UP);
    }
} 