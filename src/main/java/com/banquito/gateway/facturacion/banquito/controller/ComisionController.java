package com.banquito.gateway.facturacion.banquito.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.gateway.facturacion.banquito.controller.dto.ComisionDTO;
import com.banquito.gateway.facturacion.banquito.controller.mapper.ComisionMapper;
import com.banquito.gateway.facturacion.banquito.service.ComisionService;
import com.banquito.gateway.facturacion.banquito.service.exception.CreateException;
import com.banquito.gateway.facturacion.banquito.service.exception.NotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/comisiones")
@RequiredArgsConstructor
@Slf4j
public class ComisionController {

    private final ComisionService comisionService;
    private final ComisionMapper comisionMapper;

    @GetMapping
    public ResponseEntity<List<ComisionDTO>> obtenerComisiones() {
        log.info("Obteniendo todas las comisiones");
        return ResponseEntity.ok(
            this.comisionService.findAll().stream()
                .map(comisionMapper::toDTO)
                .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComisionDTO> obtenerComision(@PathVariable("id") String id) {
        log.info("Obteniendo comisión con ID: {}", id);
        try {
            return ResponseEntity.ok(
                this.comisionMapper.toDTO(
                    this.comisionService.findById(id)
                )
            );
        } catch (NotFoundException e) {
            log.warn("No se encontró la comisión con ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/codigo/{codComision}")
    public ResponseEntity<ComisionDTO> obtenerComisionPorCodigo(@PathVariable("codComision") String codComision) {
        log.info("Obteniendo comisión con código: {}", codComision);
        try {
            return ResponseEntity.ok(
                this.comisionMapper.toDTO(
                    this.comisionService.findByCodComision(codComision)
                )
            );
        } catch (NotFoundException e) {
            log.warn("No se encontró la comisión con código: {}", codComision);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ComisionDTO> crearComision(@Valid @RequestBody ComisionDTO comisionDTO) {
        log.info("Creando nueva comisión");
        try {
            return ResponseEntity.ok(
                this.comisionMapper.toDTO(
                    this.comisionService.create(
                        this.comisionMapper.toModel(comisionDTO)
                    )
                )
            );
        } catch (CreateException e) {
            log.error("Error al crear comisión: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarComision(@PathVariable("id") String id, 
            @Valid @RequestBody ComisionDTO comisionDTO) {
        log.info("Actualizando comisión con ID: {}", id);
        try {
            comisionDTO.setId(id);
            this.comisionService.update(
                this.comisionMapper.toModel(comisionDTO)
            );
            return ResponseEntity.ok().build();
        } catch (CreateException e) {
            log.error("Error al actualizar comisión: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarComision(@PathVariable("id") String id) {
        log.info("Eliminando comisión con ID: {}", id);
        try {
            this.comisionService.delete(id);
            return ResponseEntity.ok().build();
        } catch (CreateException e) {
            log.error("Error al eliminar comisión: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}