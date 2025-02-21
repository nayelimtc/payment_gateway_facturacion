package com.banquito.gateway.facturacion.banquito.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.gateway.facturacion.banquito.model.Comision;
import com.banquito.gateway.facturacion.banquito.repository.ComisionRepository;
import com.banquito.gateway.facturacion.banquito.service.exception.CreateException;
import com.banquito.gateway.facturacion.banquito.service.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComisionService {

    private final ComisionRepository comisionRepository;

    public List<Comision> findAll() {
        return this.comisionRepository.findAll();
    }

    public Comision findById(String id) {
        Optional<Comision> comisionOpt = this.comisionRepository.findById(id);
        if (comisionOpt.isPresent()) {
            return comisionOpt.get();
        } else {
            throw new NotFoundException("Comisión", "id: " + id);
        }
    }

    @Transactional
    public Comision create(Comision comision) {
        try {
            return this.comisionRepository.save(comision);
        } catch (Exception e) {
            throw new CreateException("Comisión", "Error al crear la comisión: " + e.getMessage());
        }
    }

    @Transactional
    public void update(Comision comision) {
        try {
            this.comisionRepository.save(comision);
        } catch (Exception e) {
            throw new CreateException("Comisión", "Error al actualizar la comisión: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(String id) {
        try {
            this.comisionRepository.deleteById(id);
        } catch (Exception e) {
            throw new CreateException("Comisión", "Error al eliminar la comisión: " + e.getMessage());
        }
    }
}