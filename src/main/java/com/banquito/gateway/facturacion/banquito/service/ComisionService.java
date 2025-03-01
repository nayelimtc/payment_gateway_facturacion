package com.banquito.gateway.facturacion.banquito.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
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

    public List<Comision> findByTipo(String tipo) {
        log.debug("Buscando comisiones por tipo: {}", tipo);
        if (!tipo.equals("POR") && !tipo.equals("FIJ")) {
            throw new CreateException("Comisión", "Tipo de comisión no válido: " + tipo);
        }
        return this.comisionRepository.findByTipo(tipo);
    }

    public List<Comision> findByManejaSegmentos(Boolean manejaSegmentos) {
        log.debug("Buscando comisiones por manejo de segmentos: {}", manejaSegmentos);
        return this.comisionRepository.findByManejaSegmentos(manejaSegmentos);
    }

    public Comision findById(String id) {
        log.debug("Buscando comisión por ID: {}", id);
        Optional<Comision> comisionOpt = this.comisionRepository.findById(new ObjectId(id));
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
            log.debug("Creando nueva comisión: {}", comision);
            comision.prePersist();
            return this.comisionRepository.save(comision);
        } catch (Exception e) {
            log.error("Error al crear comisión", e);
            throw new CreateException("Comisión", "Error al crear la comisión: " + e.getMessage());
        }
    }

    @Transactional
    public void update(Comision comision) {
        try {
            log.debug("Actualizando comisión: {}", comision);
            this.comisionRepository.save(comision);
        } catch (Exception e) {
            log.error("Error al actualizar comisión", e);
            throw new CreateException("Comisión", "Error al actualizar la comisión: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(String id) {
        try {
            log.debug("Eliminando comisión con ID: {}", id);
            this.comisionRepository.deleteById(new ObjectId(id));
        } catch (Exception e) {
            log.error("Error al eliminar comisión", e);
            throw new CreateException("Comisión", "Error al eliminar la comisión: " + e.getMessage());
        }
    }
}