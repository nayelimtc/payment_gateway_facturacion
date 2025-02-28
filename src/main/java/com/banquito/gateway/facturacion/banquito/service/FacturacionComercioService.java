package com.banquito.gateway.facturacion.banquito.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
    private final CalculadoraComisionService calculadoraComisionService;

    public List<FacturacionComercio> findAll() {
        log.debug("Obteniendo todas las facturaciones");
        return this.facturacionComercioRepository.findAll();
    }

    public FacturacionComercio findById(String id) {
        log.debug("Buscando facturación por ID: {}", id);
        Optional<FacturacionComercio> facturacionOpt = this.facturacionComercioRepository.findById(new ObjectId(id));
        if (facturacionOpt.isPresent()) {
            return facturacionOpt.get();
        } else {
            throw new NotFoundException("Facturación Comercio", "id: " + id);
        }
    }

    public List<FacturacionComercio> findByComercio(String codComercio, String orderBy, Direction direction) {
        log.debug("Buscando facturaciones por código de comercio: {} ordenado por: {} {}", 
            codComercio, orderBy, direction);
        return this.facturacionComercioRepository.findByCodComercio(
            codComercio, 
            Sort.by(direction, orderBy));
    }

    public List<FacturacionComercio> findByComercioAndEstado(String codComercio, String estado, 
            String orderBy, Direction direction) {
        log.debug("Buscando facturaciones por código de comercio: {} y estado: {} ordenado por: {} {}", 
            codComercio, estado, orderBy, direction);
        return this.facturacionComercioRepository.findByCodComercioAndEstado(
            codComercio, 
            estado,
            Sort.by(direction, orderBy));
    }

    public List<FacturacionComercio> findByFechas(LocalDate fechaInicio, LocalDate fechaFin, 
            String orderBy, Direction direction) {
        log.debug("Buscando facturaciones entre fechas: {} - {} ordenado por: {} {}", 
            fechaInicio, fechaFin, orderBy, direction);
        return this.facturacionComercioRepository.findByFechaInicioBetween(
            fechaInicio, 
            fechaFin,
            Sort.by(direction, orderBy));
    }

    public List<FacturacionComercio> findByComercioAndFechas(String codComercio, 
            LocalDate fechaInicio, LocalDate fechaFin, String orderBy, Direction direction) {
        log.debug("Buscando facturaciones por comercio: {} entre fechas: {} - {} ordenado por: {} {}", 
            codComercio, fechaInicio, fechaFin, orderBy, direction);
        return this.facturacionComercioRepository.findByCodComercioAndFechaInicioBetween(
            codComercio,
            fechaInicio, 
            fechaFin,
            Sort.by(direction, orderBy));
    }

    public List<FacturacionComercio> findByEstado(String estado, String orderBy, Direction direction) {
        log.debug("Buscando facturaciones por estado: {} ordenado por: {} {}", 
            estado, orderBy, direction);
        return this.facturacionComercioRepository.findByEstado(
            estado,
            Sort.by(direction, orderBy));
    }

    @Transactional
    public FacturacionComercio create(FacturacionComercio facturacion) {
        try {
            log.debug("Creando nueva facturación: {}", facturacion);
            facturacion.prePersist();
            facturacion.setEstado("PEN");
            facturacion.setFechaFacturacion(LocalDate.now());
            
            // Calculamos la comisión
            BigDecimal comision = this.calculadoraComisionService.calcularComision(
                facturacion.getCodComision(),
                facturacion.getTransaccionesProcesadas(),
                facturacion.getValor()
            );
            facturacion.setValor(comision);
            
            return this.facturacionComercioRepository.save(facturacion);
        } catch (Exception e) {
            log.error("Error al crear facturación", e);
            throw new CreateException("Facturación Comercio", "Error al crear la facturación: " + e.getMessage());
        }
    }

    @Transactional
    public void update(FacturacionComercio facturacion) {
        try {
            log.debug("Actualizando facturación: {}", facturacion);
            if ("PAG".equals(facturacion.getEstado())) {
                facturacion.setFechaPago(LocalDate.now());
            }
            this.facturacionComercioRepository.save(facturacion);
        } catch (Exception e) {
            log.error("Error al actualizar facturación", e);
            throw new CreateException("Facturación Comercio", "Error al actualizar la facturación: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(String id) {
        try {
            log.debug("Eliminando facturación con ID: {}", id);
            this.facturacionComercioRepository.deleteById(new ObjectId(id));
        } catch (Exception e) {
            log.error("Error al eliminar facturación", e);
            throw new CreateException("Facturación Comercio", "Error al eliminar la facturación: " + e.getMessage());
        }
    }
}