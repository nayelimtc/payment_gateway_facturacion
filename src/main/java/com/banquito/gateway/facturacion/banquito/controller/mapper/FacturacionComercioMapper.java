package com.banquito.gateway.facturacion.banquito.controller.mapper;

import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.banquito.gateway.facturacion.banquito.controller.dto.FacturacionComercioDTO;
import com.banquito.gateway.facturacion.banquito.model.FacturacionComercio;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FacturacionComercioMapper {
    @Mapping(target = "id", source = "id", qualifiedByName = "objectIdToString")
    FacturacionComercioDTO toDTO(FacturacionComercio model);

    @Mapping(target = "id", source = "id", qualifiedByName = "stringToObjectId")
    FacturacionComercio toModel(FacturacionComercioDTO dto);

    @Named("objectIdToString")
    default String objectIdToString(ObjectId id) {
        return id != null ? id.toString() : null;
    }

    @Named("stringToObjectId")
    default ObjectId stringToObjectId(String id) {
        return id != null ? new ObjectId(id) : null;
    }
}