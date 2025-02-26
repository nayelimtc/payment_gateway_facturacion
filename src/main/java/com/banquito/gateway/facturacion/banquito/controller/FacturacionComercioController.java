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
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/facturaciones")
@RequiredArgsConstructor
@Slf4j
public class FacturacionComercioController {

    private final FacturacionComercioService facturacionService;
    private final FacturacionComercioMapper facturacionMapper;

    @GetMapping
    public ResponseEntity<List<FacturacionComercioDTO>> obtenerFacturaciones() {
        log.info("Obteniendo todas las facturaciones");
        return ResponseEntity.ok(
            this.facturacionService.findAll().stream()
                .map(facturacionMapper::toDTO)
                .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturacionComercioDTO> obtenerFacturacion(@PathVariable("id") String id) {
        log.info("Obteniendo facturación con ID: {}", id);
        try {
            return ResponseEntity.ok(
                this.facturacionMapper.toDTO(
                    this.facturacionService.findById(id)
                )
            );
        } catch (NotFoundException e) {
            log.warn("No se encontró la facturación con ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/codigo/{codFacturacionComercio}")
    public ResponseEntity<FacturacionComercioDTO> obtenerFacturacionPorCodigo(
            @PathVariable("codFacturacionComercio") String codFacturacionComercio) {
        log.info("Obteniendo facturación con código: {}", codFacturacionComercio);
        try {
            return ResponseEntity.ok(
                this.facturacionMapper.toDTO(
                    this.facturacionService.findByCodFacturacionComercio(codFacturacionComercio)
                )
            );
        } catch (NotFoundException e) {
            log.warn("No se encontró la facturación con código: {}", codFacturacionComercio);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/comercio/{codComercio}")
    public ResponseEntity<List<FacturacionComercioDTO>> obtenerFacturacionesPorComercio(
            @PathVariable("codComercio") String codComercio) {
        log.info("Obteniendo facturaciones por código de comercio: {}", codComercio);
        return ResponseEntity.ok(
            this.facturacionService.findByComercio(codComercio).stream()
                .map(facturacionMapper::toDTO)
                .toList()
        );
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<FacturacionComercioDTO>> obtenerFacturacionesPorFechas(
            @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        log.info("Obteniendo facturaciones entre fechas: {} y {}", fechaInicio, fechaFin);
        return ResponseEntity.ok(
            this.facturacionService.findByFechas(fechaInicio, fechaFin).stream()
                .map(facturacionMapper::toDTO)
                .toList()
        );
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<FacturacionComercioDTO>> obtenerFacturacionesPorEstado(
            @PathVariable("estado") String estado) {
        log.info("Obteniendo facturaciones por estado: {}", estado);
        return ResponseEntity.ok(
            this.facturacionService.findByEstado(estado).stream()
                .map(facturacionMapper::toDTO)
                .toList()
        );
    }

    @PostMapping
    public ResponseEntity<FacturacionComercioDTO> crearFacturacion(
            @Valid @RequestBody FacturacionComercioDTO facturacionDTO) {
        log.info("Creando nueva facturación");
        try {
            return ResponseEntity.ok(
                this.facturacionMapper.toDTO(
                    this.facturacionService.create(
                        this.facturacionMapper.toModel(facturacionDTO)
                    )
                )
            );
        } catch (CreateException e) {
            log.error("Error al crear facturación: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarFacturacion(@PathVariable("id") String id,
            @Valid @RequestBody FacturacionComercioDTO facturacionDTO) {
        log.info("Actualizando facturación con ID: {}", id);
        try {
            facturacionDTO.setId(id);
            this.facturacionService.update(
                this.facturacionMapper.toModel(facturacionDTO)
            );
            return ResponseEntity.ok().build();
        } catch (CreateException e) {
            log.error("Error al actualizar facturación: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFacturacion(@PathVariable("id") String id) {
        log.info("Eliminando facturación con ID: {}", id);
        try {
            this.facturacionService.delete(id);
            return ResponseEntity.ok().build();
        } catch (CreateException e) {
            log.error("Error al eliminar facturación: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}