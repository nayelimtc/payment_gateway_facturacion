package com.banquito.gateway.facturacion.banquito.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.gateway.facturacion.banquito.model.FacturacionComercio;
import com.banquito.gateway.facturacion.banquito.repository.FacturacionComercioRepository;
import com.banquito.gateway.facturacion.banquito.service.exception.CreateException;
import com.banquito.gateway.facturacion.banquito.service.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacturacionComercioService {

    private final FacturacionComercioRepository facturacionComercioRepository;

    public List<FacturacionComercio> findAll() {
        return this.facturacionComercioRepository.findAll();
    }

    public FacturacionComercio findById(String id) {
        Optional<FacturacionComercio> facturacionOpt = this.facturacionComercioRepository.findById(id);
        if (facturacionOpt.isPresent()) {
            return facturacionOpt.get();
        } else {
            throw new NotFoundException("Facturación Comercio", "id: " + id);
        }
    }

    public List<FacturacionComercio> findByComercio(String codComercio) {
        return this.facturacionComercioRepository.findByCodComercio(codComercio);
    }

    public List<FacturacionComercio> findByFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return this.facturacionComercioRepository.findByFechaInicioBetween(fechaInicio, fechaFin);
    }

    public List<FacturacionComercio> findByEstado(String estado) {
        return this.facturacionComercioRepository.findByEstado(estado);
    }

    @Transactional
    public FacturacionComercio create(FacturacionComercio facturacion) {
        try {
            facturacion.setEstado("PEN");
            facturacion.setFechaFacturacion(LocalDate.now());
            return this.facturacionComercioRepository.save(facturacion);
        } catch (Exception e) {
            throw new CreateException("Facturación Comercio", "Error al crear la facturación: " + e.getMessage());
        }
    }

    @Transactional
    public void update(FacturacionComercio facturacion) {
        try {
            if ("PAG".equals(facturacion.getEstado())) {
                facturacion.setFechaPago(LocalDate.now());
            }
            this.facturacionComercioRepository.save(facturacion);
        } catch (Exception e) {
            throw new CreateException("Facturación Comercio", "Error al actualizar la facturación: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(String id) {
        try {
            this.facturacionComercioRepository.deleteById(id);
        } catch (Exception e) {
            throw new CreateException("Facturación Comercio", "Error al eliminar la facturación: " + e.getMessage());
        }
    }
}