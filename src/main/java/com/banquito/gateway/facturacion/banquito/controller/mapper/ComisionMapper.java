package com.banquito.gateway.facturacion.banquito.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.gateway.facturacion.banquito.controller.dto.ComisionDTO;
import com.banquito.gateway.facturacion.banquito.model.Comision;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ComisionMapper {
    @Mapping(target = "id", source = "id")
    ComisionDTO toDTO(Comision model);

    @Mapping(target = "id", source = "id")
    Comision toModel(ComisionDTO dto);
}