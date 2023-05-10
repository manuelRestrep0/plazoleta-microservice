package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.CategoriaEntity;
import com.pragma.plazoletamicroservice.domain.model.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoriaEntityMapper {
    Categoria toCategoria(CategoriaEntity categoriaEntity);

    CategoriaEntity toEntity(Categoria categoria);
}
