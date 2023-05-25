package com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.entity.PlatoEntity;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper.IPlatoEntityMapper;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.IPlatoRepository;
import com.pragma.plazoletamicroservice.configuration.Constants;
import com.pragma.plazoletamicroservice.domain.exceptions.PlatoNoEncontradoException;
import com.pragma.plazoletamicroservice.domain.model.Plato;
import com.pragma.plazoletamicroservice.domain.spi.IPlatoPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
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
    public Plato obtenerPlato(Long id) {
        Optional<PlatoEntity> platoEntity = platoRepository.findById(id);
        if(platoEntity.isEmpty()){
            throw new PlatoNoEncontradoException(Constants.PLATO_NO_REGISTRADO);
        }
        return platoEntityMapper.toPlato(platoEntity.get());
    }

    @Override
    public List<Page<Plato>> obtenerPlatos(String nombre, Long id, int elementos) {
        List<Page<Plato>> paginas = new ArrayList<>();
        int numeroPagina = 0;
        Page<Plato> pagina;
        do{
            Pageable pageable = PageRequest.of(numeroPagina,elementos, Sort.by("nombre"));
            if(nombre.equals("all")){
                pagina = platoRepository.findAllByIdRestaurante_Id(id,pageable).map(platoEntityMapper::toPlato);
            } else {
                pagina = platoRepository.findAllByIdCategoria_NombreAndIdRestaurante_Id(nombre,id,pageable).map(platoEntityMapper::toPlato);
            }
            paginas.add(pagina);
            numeroPagina++;
        } while (pagina.hasNext());
        return paginas;
    }
}
