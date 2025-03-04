package com.banquito.gateway.facturacion.banquito.controller.mapper;

import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.banquito.gateway.facturacion.banquito.controller.dto.ComisionDTO;
import com.banquito.gateway.facturacion.banquito.model.Comision;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ComisionMapper {
    @Mapping(target = "id", source = "id", qualifiedByName = "objectIdToString")
    ComisionDTO toDTO(Comision model);

    @Mapping(target = "id", source = "id", qualifiedByName = "stringToObjectId")
    Comision toModel(ComisionDTO dto);

    @Named("objectIdToString")
    default String objectIdToString(ObjectId id) {
        return id != null ? id.toString() : null;
    }

    @Named("stringToObjectId")
    default ObjectId stringToObjectId(String id) {
        return id != null ? new ObjectId(id) : null;
    }
}