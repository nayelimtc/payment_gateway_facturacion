package com.banquito.gateway.facturacion.banquito.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.gateway.facturacion.banquito.model.FacturacionComercio;
import com.banquito.gateway.facturacion.banquito.repository.FacturacionComercioRepository;
import com.banquito.gateway.facturacion.banquito.service.exception.CreateException;
import com.banquito.gateway.facturacion.banquito.service.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FacturacionComercioService {
    
    private final FacturacionComercioRepository facturacionComercioRepository;

    public List<FacturacionComercio> findAll() {
        log.debug("Obteniendo todas las facturaciones");
        return this.facturacionComercioRepository.findAll();
    }

    public FacturacionComercio findById(String id) {
        log.debug("Buscando facturación por ID: {}", id);
        Optional<FacturacionComercio> facturacionOpt = this.facturacionComercioRepository.findById(id);
        if (facturacionOpt.isPresent()) {
            return facturacionOpt.get();
        } else {
            throw new NotFoundException("Facturación Comercio", "id: " + id);
        }
    }
    
    public FacturacionComercio findByCodFacturacionComercio(String codFacturacionComercio) {
        log.debug("Buscando facturación por código: {}", codFacturacionComercio);
        Optional<FacturacionComercio> facturacionOpt = this.facturacionComercioRepository.findByCodFacturacionComercio(codFacturacionComercio);
        if (facturacionOpt.isPresent()) {
            return facturacionOpt.get();
        } else {
            throw new NotFoundException("Facturación Comercio", "código: " + codFacturacionComercio);
        }
    }

    public List<FacturacionComercio> findByComercio(String codComercio) {
        log.debug("Buscando facturaciones por código de comercio: {}", codComercio);
        return this.facturacionComercioRepository.findByCodComercio(codComercio);
    }

    public List<FacturacionComercio> findByFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        log.debug("Buscando facturaciones entre fechas: {} y {}", fechaInicio, fechaFin);
        return this.facturacionComercioRepository.findByFechaInicioBetween(fechaInicio, fechaFin);
    }

    public List<FacturacionComercio> findByEstado(String estado) {
        log.debug("Buscando facturaciones por estado: {}", estado);
        return this.facturacionComercioRepository.findByEstado(estado);
    }

    @Transactional
    public FacturacionComercio create(FacturacionComercio facturacion) {
        try {
            log.debug("Creando nueva facturación");
            if (facturacion.getCodFacturacionComercio() == null) {
                facturacion.setCodFacturacionComercio(UUID.randomUUID().toString().substring(0, 10));
            }
            facturacion.setEstado("PEN");
            facturacion.setFechaFacturacion(LocalDate.now());
            return this.facturacionComercioRepository.save(facturacion);
        } catch (Exception e) {
            log.error("Error al crear facturación: {}", e.getMessage());
            throw new CreateException("Facturación Comercio", "Error al crear la facturación: " + e.getMessage());
        }
    }

    @Transactional
    public void update(FacturacionComercio facturacion) {
        try {
            log.debug("Actualizando facturación con ID: {}", facturacion.getId());
            if ("PAG".equals(facturacion.getEstado())) {
                facturacion.setFechaPago(LocalDate.now());
            }
            this.facturacionComercioRepository.save(facturacion);
        } catch (Exception e) {
            log.error("Error al actualizar facturación: {}", e.getMessage());
            throw new CreateException("Facturación Comercio", "Error al actualizar la facturación: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(String id) {
        try {
            log.debug("Eliminando facturación con ID: {}", id);
            this.facturacionComercioRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error al eliminar facturación: {}", e.getMessage());
            throw new CreateException("Facturación Comercio", "Error al eliminar la facturación: " + e.getMessage());
        }
    }
}