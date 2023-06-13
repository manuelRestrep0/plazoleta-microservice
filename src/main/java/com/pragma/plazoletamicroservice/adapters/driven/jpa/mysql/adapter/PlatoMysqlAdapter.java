package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.PlatoEntity;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper.IPlatoEntityMapper;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.IPlatoRepository;
import com.pragma.plazoletamicroservice.domain.model.Plato;
import com.pragma.plazoletamicroservice.domain.spi.IPlatoPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@RequiredArgsConstructor
public class PlatoMysqlAdapter implements IPlatoPersistencePort {
    private final IPlatoRepository platoRepository;
    private final IPlatoEntityMapper platoEntityMapper;

    @Override
    public void guardarPlato(Plato plato) {
        platoRepository.save(platoEntityMapper.toEntity(plato));
    }
    @Override
    public Optional<Plato> obtenerPlato(Long id) {
        Optional<PlatoEntity> platoEntity = platoRepository.findById(id);
        return platoEntity.map(platoEntityMapper::toPlato);
    }

    @Override
    public Page<Plato> obtenerPlatos(String nombre, Long id, int elementos, int numeroPagina) {
        Page<Plato> pagina;
        Pageable pageable = PageRequest.of(numeroPagina,elementos, Sort.by("nombre"));
        if(nombre.equals("all")){
            pagina = platoRepository.findAllByIdRestaurante_Id(id,pageable).map(platoEntityMapper::toPlato);
        } else {
            pagina = platoRepository.findAllByIdCategoria_NombreAndIdRestaurante_Id(nombre,id,pageable).map(platoEntityMapper::toPlato);
        }
        return pagina;
    }

    @Override
    public Boolean verificarRestaurantePlato(Long idRestaurante, Long idPlato) {
        return platoRepository.existsByIdAndIdRestaurante_Id(idPlato,idRestaurante);
    }
}
