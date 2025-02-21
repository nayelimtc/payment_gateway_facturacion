package com.banquito.gateway.facturacion.banquito.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.gateway.facturacion.banquito.controller.dto.FacturacionComercioDTO;
import com.banquito.gateway.facturacion.banquito.controller.mapper.FacturacionComercioMapper;
import com.banquito.gateway.facturacion.banquito.service.FacturacionComercioService;
import com.banquito.gateway.facturacion.banquito.service.exception.CreateException;
import com.banquito.gateway.facturacion.banquito.service.exception.NotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/facturaciones")
@RequiredArgsConstructor
public class FacturacionComercioController {

    private final FacturacionComercioService facturacionService;
    private final FacturacionComercioMapper facturacionMapper;

    @GetMapping
    public ResponseEntity<List<FacturacionComercioDTO>> obtenerFacturaciones() {
        return ResponseEntity.ok(
                this.facturacionService.findAll().stream()
                        .map(facturacionMapper::toDTO)
                        .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturacionComercioDTO> obtenerFacturacion(@PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(
                    this.facturacionMapper.toDTO(
                            this.facturacionService.findById(id)));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/comercio/{codComercio}")
    public ResponseEntity<List<FacturacionComercioDTO>> obtenerFacturacionesPorComercio(
            @PathVariable("codComercio") String codComercio) {
        return ResponseEntity.ok(
                this.facturacionService.findByComercio(codComercio).stream()
                        .map(facturacionMapper::toDTO)
                        .toList());
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<FacturacionComercioDTO>> obtenerFacturacionesPorFechas(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return ResponseEntity.ok(
                this.facturacionService.findByFechas(fechaInicio, fechaFin).stream()
                        .map(facturacionMapper::toDTO)
                        .toList());
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<FacturacionComercioDTO>> obtenerFacturacionesPorEstado(
            @PathVariable("estado") String estado) {
        return ResponseEntity.ok(
                this.facturacionService.findByEstado(estado).stream()
                        .map(facturacionMapper::toDTO)
                        .toList());
    }

    @PostMapping
    public ResponseEntity<FacturacionComercioDTO> crearFacturacion(
            @Valid @RequestBody FacturacionComercioDTO facturacionDTO) {
        try {
            return ResponseEntity.ok(
                    this.facturacionMapper.toDTO(
                            this.facturacionService.create(
                                    this.facturacionMapper.toModel(facturacionDTO))));
        } catch (CreateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarFacturacion(@PathVariable("id") String id,
            @Valid @RequestBody FacturacionComercioDTO facturacionDTO) {
        try {
            facturacionDTO.setCodFacturacionComercio(id);
            this.facturacionService.update(
                    this.facturacionMapper.toModel(facturacionDTO));
            return ResponseEntity.ok().build();
        } catch (CreateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFacturacion(@PathVariable("id") String id) {
        try {
            this.facturacionService.delete(id);
            return ResponseEntity.ok().build();
        } catch (CreateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}