package com.banquito.gateway.facturacion.banquito.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.banquito.gateway.facturacion.banquito.model.Comision;

@Repository
public interface ComisionRepository extends MongoRepository<Comision, ObjectId> {
    Optional<Comision> findByCodComision(String codComision);

    List<Comision> findByTipo(String tipo);

    List<Comision> findByManejaSegmentos(Boolean manejaSegmentos);
}