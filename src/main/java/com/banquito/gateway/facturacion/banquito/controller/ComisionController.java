package com.banquito.gateway.facturacion.banquito.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/comisiones")
@Tag(name = "Comisiones", description = "API para gestión de comisiones del payment gateway")
public class ComisionController {

    @Autowired
    private ComisionService comisionService;
    
    @Autowired
    private ComisionMapper comisionMapper;

    @GetMapping
    @Operation(summary = "Listar todas las comisiones", description = "Obtiene la lista completa de comisiones configuradas para el cobro a comercios")
    public ResponseEntity<List<ComisionDTO>> obtenerComisiones() {
        return ResponseEntity.ok(
                this.comisionService.findAll().stream()
                        .map(comisionMapper::toDTO)
                        .toList());
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Buscar comisiones por tipo", description = "Obtiene las comisiones filtradas por tipo (POR: Porcentual, FIJ: Fijo)")
    public ResponseEntity<List<ComisionDTO>> obtenerComisionesPorTipo(
            @Parameter(description = "Tipo de comisión (POR: Porcentual, FIJ: Fijo)", required = true) @PathVariable("tipo") String tipo) {
        return ResponseEntity.ok(
                this.comisionService.findByTipo(tipo).stream()
                        .map(comisionMapper::toDTO)
                        .toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar comisión por ID", description = "Obtiene una comisión específica por su ID para consultar su configuración")
    public ResponseEntity<ComisionDTO> obtenerComision(
            @Parameter(description = "ID de la comisión", required = true) @PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(
                    this.comisionMapper.toDTO(
                            this.comisionService.findById(id)));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear comisión", description = "Crea una nueva configuración de comisión para el cobro a comercios")
    public ResponseEntity<ComisionDTO> crearComision(
            @Parameter(description = "Datos de la comisión a crear", required = true) @Valid @RequestBody ComisionDTO comisionDTO) {
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
    @Operation(summary = "Actualizar comisión", description = "Actualiza la configuración de una comisión existente")
    public ResponseEntity<Void> actualizarComision(
            @Parameter(description = "ID de la comisión", required = true) @PathVariable("id") String id,
            @Parameter(description = "Datos actualizados de la comisión", required = true) @Valid @RequestBody ComisionDTO comisionDTO) {
        try {
            comisionDTO.setCodComision(id.substring(0, 8));
            this.comisionService.update(
                    this.comisionMapper.toModel(comisionDTO));
            return ResponseEntity.ok().build();
        } catch (CreateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar comisión", description = "Elimina una configuración de comisión existente si no está siendo utilizada")
    public ResponseEntity<Void> eliminarComision(
            @Parameter(description = "ID de la comisión", required = true) @PathVariable("id") String id) {
        try {
            this.comisionService.delete(id);
            return ResponseEntity.ok().build();
        } catch (CreateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}