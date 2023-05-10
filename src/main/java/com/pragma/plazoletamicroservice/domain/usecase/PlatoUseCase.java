package com.pragma.plazoletamicroservice.domain.usecase;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.CategoriaEntity;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.PlatoEntity;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.RestauranteEntity;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper.ICategoriaEntityMapper;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper.IPlatoEntityMapper;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper.IRestauranteEntityMapper;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.ICategoriaRepository;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.IPlatoRepository;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.IRestauranteRepository;
import com.pragma.plazoletamicroservice.configuration.Constants;
import com.pragma.plazoletamicroservice.domain.api.IPlatoServicePort;
import com.pragma.plazoletamicroservice.domain.exceptions.PlatoNoEncontradoException;
import com.pragma.plazoletamicroservice.domain.exceptions.RestauranteNoEncontradoException;
import com.pragma.plazoletamicroservice.domain.model.Plato;
import com.pragma.plazoletamicroservice.domain.spi.IPlatoPersistencePort;

import java.util.Optional;

public class PlatoUseCase implements IPlatoServicePort {
    private final IPlatoPersistencePort platoPersistencePort;
    private final IPlatoRepository platoRepository;
    private final IPlatoEntityMapper platoEntityMapper;
    private final IRestauranteRepository restauranteRepository;
    private final IRestauranteEntityMapper restauranteEntityMapper;
    private final ICategoriaRepository categoriaRepository;
    private final ICategoriaEntityMapper categoriaEntityMapper;

    public PlatoUseCase(IPlatoPersistencePort platoPersistencePort, IPlatoRepository platoRepository, IPlatoEntityMapper platoEntityMapper, IRestauranteRepository restauranteRepository, IRestauranteEntityMapper restauranteEntityMapper, ICategoriaRepository categoriaRepository, ICategoriaEntityMapper categoriaEntityMapper) {
        this.platoPersistencePort = platoPersistencePort;
        this.platoRepository = platoRepository;
        this.platoEntityMapper = platoEntityMapper;
        this.restauranteRepository = restauranteRepository;
        this.restauranteEntityMapper = restauranteEntityMapper;
        this.categoriaRepository = categoriaRepository;
        this.categoriaEntityMapper = categoriaEntityMapper;
    }

    @Override
    public void crearPlato(Plato plato) {
        plato.setActivo(true);

        Optional<RestauranteEntity> restaurante = restauranteRepository.findById(plato.getIdRestauranteAux());
        if (restaurante.isPresent()){
            plato.setIdRestaurante(restauranteEntityMapper.toRestaurante(restaurante.get()));
        } else {
            throw new RestauranteNoEncontradoException(Constants.RESTAURANTE_NO_ENCONTRADO);
        }

        Optional<CategoriaEntity> categoria = categoriaRepository.findById(plato.getIdCategoriaAux());

        if(categoria.isPresent()){
            plato.setIdCategoria(categoriaEntityMapper.toCategoria(categoria.get()));
        } else{
            throw new PlatoNoEncontradoException(Constants.CATEGORIA_NO_ENCONTRADA);
        }
        this.platoPersistencePort.crearPlato(plato);

    }
    @Override
    public void modificarPlato(Long id,String precio, String descripcion) {
        Optional<PlatoEntity> plato = platoRepository.findById(id);
        if(plato.isEmpty()){
            throw new PlatoNoEncontradoException(Constants.PLATO_NO_REGISTRADO);
        }
        Plato platoModificado = platoEntityMapper.toPlato(plato.get());
        if(precio != null){
            platoModificado.setPrecio(precio);
        }
        if(descripcion != null){
            platoModificado.setDescripcion(descripcion);
        }
        platoPersistencePort.modificarPlato(platoModificado);
    }
}
