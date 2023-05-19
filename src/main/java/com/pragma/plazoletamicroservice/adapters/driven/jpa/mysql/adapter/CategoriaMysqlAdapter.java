package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.CategoriaEntity;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper.ICategoriaEntityMapper;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.ICategoriaRepository;
import com.pragma.plazoletamicroservice.configuration.Constants;
import com.pragma.plazoletamicroservice.domain.exceptions.CategoriaNoEncontradaException;
import com.pragma.plazoletamicroservice.domain.model.Categoria;
import com.pragma.plazoletamicroservice.domain.spi.ICategoriaPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CategoriaMysqlAdapter implements ICategoriaPersistencePort {

    private final ICategoriaRepository categoriaRepository;
    private final ICategoriaEntityMapper categoriaEntityMapper;


    @Override
    public Categoria obtenerCategoria(Long id) {
        Optional<CategoriaEntity> categoriaEntity = categoriaRepository.findById(id);
        if(categoriaEntity.isEmpty()){
            throw new CategoriaNoEncontradaException(Constants.CATEGORIA_NO_ENCONTRADA);
        }
        return categoriaEntityMapper.toCategoria(categoriaEntity.get());
    }
}
