package com.banquito.gateway.facturacion.banquito.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.gateway.facturacion.banquito.controller.dto.FacturacionComercioDTO;
import com.banquito.gateway.facturacion.banquito.model.FacturacionComercio;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FacturacionComercioMapper {
    @Mapping(target = "id", source = "id")
    FacturacionComercioDTO toDTO(FacturacionComercio model);

    @Mapping(target = "id", source = "id")
    FacturacionComercio toModel(FacturacionComercioDTO dto);
}