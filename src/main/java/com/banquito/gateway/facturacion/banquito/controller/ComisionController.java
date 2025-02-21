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

@RestController
@RequestMapping("/v1/comisiones")
@RequiredArgsConstructor
public class ComisionController {

    private final ComisionService comisionService;
    private final ComisionMapper comisionMapper;

    @GetMapping
    public ResponseEntity<List<ComisionDTO>> obtenerComisiones() {
        return ResponseEntity.ok(
                this.comisionService.findAll().stream()
                        .map(comisionMapper::toDTO)
                        .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComisionDTO> obtenerComision(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(
                    this.comisionMapper.toDTO(
                            this.comisionService.findById(id)));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ComisionDTO> crearComision(@Valid @RequestBody ComisionDTO comisionDTO) {
        try {
            return ResponseEntity.ok(
                    this.comisionMapper.toDTO(
                            this.comisionService.create(
                                    this.comisionMapper.toModel(comisionDTO))));
        } catch (CreateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarComision(@PathVariable("id") String id,
            @Valid @RequestBody ComisionDTO comisionDTO) {
        try {
            comisionDTO.setCodComision(id);
            this.comisionService.update(
                    this.comisionMapper.toModel(comisionDTO));
            return ResponseEntity.ok().build();
        } catch (CreateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarComision(@PathVariable("id") String id) {
        try {
            this.comisionService.delete(id);
            return ResponseEntity.ok().build();
        } catch (CreateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}