package com.banquito.gateway.facturacion.banquito.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.banquito.gateway.facturacion.banquito.model.FacturacionComercio;

@Repository
public interface FacturacionComercioRepository extends MongoRepository<FacturacionComercio, String> {
    Optional<FacturacionComercio> findByCodFacturacionComercio(String codFacturacionComercio);
    List<FacturacionComercio> findByCodComercio(String codComercio);
    List<FacturacionComercio> findByFechaInicioBetween(LocalDate fechaInicio, LocalDate fechaFin);
    List<FacturacionComercio> findByEstado(String estado);
} 