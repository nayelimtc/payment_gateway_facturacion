package com.banquito.gateway.facturacion.banquito.repository;

import java.time.LocalDate;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.banquito.gateway.facturacion.banquito.model.FacturacionComercio;

@Repository
public interface FacturacionComercioRepository extends MongoRepository<FacturacionComercio, ObjectId> {
    List<FacturacionComercio> findByCodComercio(String codComercio, Sort sort);
    List<FacturacionComercio> findByCodComercioAndEstado(String codComercio, String estado, Sort sort);
    List<FacturacionComercio> findByFechaInicioBetween(LocalDate fechaInicio, LocalDate fechaFin, Sort sort);
    List<FacturacionComercio> findByEstado(String estado, Sort sort);
    List<FacturacionComercio> findByCodComercioAndFechaInicioBetween(String codComercio, LocalDate fechaInicio, LocalDate fechaFin, Sort sort);
}