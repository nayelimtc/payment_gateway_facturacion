package com.banquito.gateway.facturacion.banquito.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.banquito.gateway.facturacion.banquito.model.Comision;

@Repository
public interface ComisionRepository extends MongoRepository<Comision, String> {
}