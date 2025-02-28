package com.banquito.gateway.facturacion.banquito.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banquito.gateway.facturacion.banquito.controller.dto.FacturacionComercioDTO;
import com.banquito.gateway.facturacion.banquito.controller.mapper.FacturacionComercioMapper;
import com.banquito.gateway.facturacion.banquito.service.FacturacionComercioService;
import com.banquito.gateway.facturacion.banquito.service.exception.CreateException;
import com.banquito.gateway.facturacion.banquito.service.exception.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/facturaciones")
@RequiredArgsConstructor
@Tag(name = "Facturaciones", description = "API para gestión de facturaciones de comercios en el payment gateway")
public class FacturacionComercioController {

    private final FacturacionComercioService facturacionService;
    private final FacturacionComercioMapper facturacionMapper;

    @GetMapping
    @Operation(summary = "Listar todas las facturaciones", description = "Obtiene la lista completa de facturaciones de comisiones a comercios")
    public ResponseEntity<List<FacturacionComercioDTO>> obtenerFacturaciones() {
        return ResponseEntity.ok(
                this.facturacionService.findAll().stream()
                        .map(facturacionMapper::toDTO)
                        .toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar facturación por ID", description = "Obtiene una facturación específica por su ID para consultar el detalle del cobro")
    public ResponseEntity<FacturacionComercioDTO> obtenerFacturacion(
            @Parameter(description = "ID de la facturación", required = true) @PathVariable("id") String id) {
        try {
            return ResponseEntity.ok(
                    this.facturacionMapper.toDTO(
                            this.facturacionService.findById(id)));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/comercio/{codComercio}")
    @Operation(summary = "Buscar facturaciones por comercio", description = "Obtiene el historial de facturaciones de un comercio específico")
    public ResponseEntity<List<FacturacionComercioDTO>> obtenerFacturacionesPorComercio(
            @Parameter(description = "Código del comercio afiliado", required = true) @PathVariable("codComercio") String codComercio,
            @Parameter(description = "Campo por el cual ordenar (fechaInicio, fechaFin, valor, etc)") @RequestParam(name = "orderBy", defaultValue = "fechaInicio") String orderBy,
            @Parameter(description = "Dirección del ordenamiento (ASC o DESC)") @RequestParam(name = "direction", defaultValue = "DESC") Direction direction) {
        return ResponseEntity.ok(
                this.facturacionService.findByComercio(codComercio, orderBy, direction).stream()
                        .map(facturacionMapper::toDTO)
                        .toList());
    }

    @GetMapping("/comercio/{codComercio}/estado/{estado}")
    @Operation(summary = "Buscar facturaciones por comercio y estado", description = "Obtiene las facturaciones de un comercio filtradas por estado (PEN: Pendiente, PAG: Pagado, ANU: Anulado)")
    public ResponseEntity<List<FacturacionComercioDTO>> obtenerFacturacionesPorComercioYEstado(
            @Parameter(description = "Código del comercio afiliado", required = true) @PathVariable("codComercio") String codComercio,
            @Parameter(description = "Estado de la facturación (PEN: Pendiente, PAG: Pagado, ANU: Anulado)", required = true) @PathVariable("estado") String estado,
            @Parameter(description = "Campo por el cual ordenar (fechaInicio, fechaFin, valor, etc)") @RequestParam(name = "orderBy", defaultValue = "fechaInicio") String orderBy,
            @Parameter(description = "Dirección del ordenamiento (ASC o DESC)") @RequestParam(name = "direction", defaultValue = "DESC") Direction direction) {
        return ResponseEntity.ok(
                this.facturacionService.findByComercioAndEstado(codComercio, estado, orderBy, direction).stream()
                        .map(facturacionMapper::toDTO)
                        .toList());
    }

    @GetMapping("/fechas")
    @Operation(summary = "Buscar facturaciones por rango de fechas", description = "Obtiene las facturaciones generadas dentro de un período específico")
    public ResponseEntity<List<FacturacionComercioDTO>> obtenerFacturacionesPorFechas(
            @Parameter(description = "Fecha de inicio del período (YYYY-MM-DD)", required = true) @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @Parameter(description = "Fecha fin del período (YYYY-MM-DD)", required = true) @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @Parameter(description = "Campo por el cual ordenar (fechaInicio, fechaFin, valor, etc)") @RequestParam(name = "orderBy", defaultValue = "fechaInicio") String orderBy,
            @Parameter(description = "Dirección del ordenamiento (ASC o DESC)") @RequestParam(name = "direction", defaultValue = "DESC") Direction direction) {
        return ResponseEntity.ok(
                this.facturacionService.findByFechas(fechaInicio, fechaFin, orderBy, direction).stream()
                        .map(facturacionMapper::toDTO)
                        .toList());
    }

    @GetMapping("/comercio/{codComercio}/fechas")
    @Operation(summary = "Buscar facturaciones por comercio y fechas", description = "Obtiene el historial de facturaciones de un comercio dentro de un período específico")
    public ResponseEntity<List<FacturacionComercioDTO>> obtenerFacturacionesPorComercioYFechas(
            @Parameter(description = "Código del comercio afiliado", required = true) @PathVariable("codComercio") String codComercio,
            @Parameter(description = "Fecha de inicio del período (YYYY-MM-DD)", required = true) @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @Parameter(description = "Fecha fin del período (YYYY-MM-DD)", required = true) @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            @Parameter(description = "Campo por el cual ordenar (fechaInicio, fechaFin, valor, etc)") @RequestParam(name = "orderBy", defaultValue = "fechaInicio") String orderBy,
            @Parameter(description = "Dirección del ordenamiento (ASC o DESC)") @RequestParam(name = "direction", defaultValue = "DESC") Direction direction) {
        return ResponseEntity.ok(
                this.facturacionService.findByComercioAndFechas(codComercio, fechaInicio, fechaFin, orderBy, direction)
                        .stream()
                        .map(facturacionMapper::toDTO)
                        .toList());
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Buscar facturaciones por estado", description = "Obtiene las facturaciones filtradas por estado (PEN: Pendiente, PAG: Pagado, ANU: Anulado)")
    public ResponseEntity<List<FacturacionComercioDTO>> obtenerFacturacionesPorEstado(
            @Parameter(description = "Estado de la facturación (PEN: Pendiente, PAG: Pagado, ANU: Anulado)", required = true) @PathVariable("estado") String estado,
            @Parameter(description = "Campo por el cual ordenar (fechaInicio, fechaFin, valor, etc)") @RequestParam(name = "orderBy", defaultValue = "fechaInicio") String orderBy,
            @Parameter(description = "Dirección del ordenamiento (ASC o DESC)") @RequestParam(name = "direction", defaultValue = "DESC") Direction direction) {
        return ResponseEntity.ok(
                this.facturacionService.findByEstado(estado, orderBy, direction).stream()
                        .map(facturacionMapper::toDTO)
                        .toList());
    }

    @PostMapping
    @Operation(summary = "Crear facturación", description = "Genera una nueva facturación de comisiones para un comercio, calculando automáticamente el valor según la configuración de comisión")
    public ResponseEntity<FacturacionComercioDTO> crearFacturacion(
            @Parameter(description = "Datos de la facturación a generar", required = true) @Valid @RequestBody FacturacionComercioDTO facturacionDTO) {
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
    @Operation(summary = "Actualizar facturación", description = "Actualiza el estado o información de una facturación existente (ej: marcar como pagada)")
    public ResponseEntity<Void> actualizarFacturacion(
            @Parameter(description = "ID de la facturación", required = true) @PathVariable("id") String id,
            @Parameter(description = "Datos actualizados de la facturación", required = true) @Valid @RequestBody FacturacionComercioDTO facturacionDTO) {
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
    @Operation(summary = "Eliminar facturación", description = "Elimina una facturación existente (solo permitido para facturaciones en estado PEN)")
    public ResponseEntity<Void> eliminarFacturacion(
            @Parameter(description = "ID de la facturación", required = true) @PathVariable("id") String id) {
        try {
            this.facturacionService.delete(id);
            return ResponseEntity.ok().build();
        } catch (CreateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}