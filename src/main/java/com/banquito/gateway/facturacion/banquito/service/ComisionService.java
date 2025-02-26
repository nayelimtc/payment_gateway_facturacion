package com.banquito.gateway.facturacion.banquito.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banquito.gateway.facturacion.banquito.model.Comision;
import com.banquito.gateway.facturacion.banquito.repository.ComisionRepository;
import com.banquito.gateway.facturacion.banquito.service.exception.CreateException;
import com.banquito.gateway.facturacion.banquito.service.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComisionService {

    private final ComisionRepository comisionRepository;

    public List<Comision> findAll() {
        log.debug("Obteniendo todas las comisiones");
        return this.comisionRepository.findAll();
    }

    public Comision findById(String id) {
        log.debug("Buscando comisión por ID: {}", id);
        Optional<Comision> comisionOpt = this.comisionRepository.findById(id);
        if (comisionOpt.isPresent()) {
            return comisionOpt.get();
        } else {
            throw new NotFoundException("Comisión", "id: " + id);
        }
    }

    public Comision findByCodComision(String codComision) {
        log.debug("Buscando comisión por código: {}", codComision);
        Optional<Comision> comisionOpt = this.comisionRepository.findByCodComision(codComision);
        if (comisionOpt.isPresent()) {
            return comisionOpt.get();
        } else {
            throw new NotFoundException("Comisión", "código: " + codComision);
        }
    }

    @Transactional
    public Comision create(Comision comision) {
        try {
            log.debug("Creando nueva comisión");
            if (comision.getCodComision() == null) {
                comision.setCodComision(UUID.randomUUID().toString().substring(0, 10));
            }
            return this.comisionRepository.save(comision);
        } catch (Exception e) {
            log.error("Error al crear comisión: {}", e.getMessage());
            throw new CreateException("Comisión", "Error al crear la comisión: " + e.getMessage());
        }
    }

    @Transactional
    public void update(Comision comision) {
        try {
            log.debug("Actualizando comisión con ID: {}", comision.getId());
            this.comisionRepository.save(comision);
        } catch (Exception e) {
            log.error("Error al actualizar comisión: {}", e.getMessage());
            throw new CreateException("Comisión", "Error al actualizar la comisión: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(String id) {
        try {
            log.debug("Eliminando comisión con ID: {}", id);
            this.comisionRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error al eliminar comisión: {}", e.getMessage());
            throw new CreateException("Comisión", "Error al eliminar la comisión: " + e.getMessage());
        }
    }
}