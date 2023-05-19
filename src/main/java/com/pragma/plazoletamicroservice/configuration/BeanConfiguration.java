package com.pragma.plazoletamicroservice.configuration;

import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.adapter.CategoriaMysqlAdapter;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.adapter.PlatoMysqlAdapter;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.adapter.RestauranteMysqlAdapter;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper.ICategoriaEntityMapper;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper.IPlatoEntityMapper;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.mapper.IRestauranteEntityMapper;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.ICategoriaRepository;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.IPlatoRepository;
import com.pragma.plazoletamicroservice.adapters.driven.jpa.mysql.repository.IRestauranteRepository;
import com.pragma.plazoletamicroservice.adapters.driving.feign.client.UsuarioFeignClient;
import com.pragma.plazoletamicroservice.adapters.driving.feign.client.UsuarioFeignHandlerImp;
import com.pragma.plazoletamicroservice.domain.api.IAuthServicePort;
import com.pragma.plazoletamicroservice.domain.api.IPlatoServicePort;
import com.pragma.plazoletamicroservice.domain.api.IRestauranteServicePort;
import com.pragma.plazoletamicroservice.domain.api.IFeignServicePort;
import com.pragma.plazoletamicroservice.domain.spi.ICategoriaPersistencePort;
import com.pragma.plazoletamicroservice.domain.spi.IPlatoPersistencePort;
import com.pragma.plazoletamicroservice.domain.spi.IRestaurantePersistencePort;
import com.pragma.plazoletamicroservice.domain.usecase.AuthUseCase;
import com.pragma.plazoletamicroservice.domain.usecase.PlatoUseCase;
import com.pragma.plazoletamicroservice.domain.usecase.RestauranteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestauranteRepository restauranteRepository;
    private final IRestauranteEntityMapper restauranteEntityMapper;
    private final IPlatoRepository platoRepository;
    private final IPlatoEntityMapper platoEntityMapper;
    private final UsuarioFeignClient usuarioFeignClient;
    private final ICategoriaRepository categoriaRepository;
    private final ICategoriaEntityMapper categoriaEntityMapper;

    @Bean
    public IRestauranteServicePort restauranteServicePort(){
        return new RestauranteUseCase(restaurantePersistencePort(), feignServicePort());
    }
    @Bean
    public IAuthServicePort authServicePort(){
        return new AuthUseCase();
    }
    @Bean
    public IFeignServicePort feignServicePort(){
        return new UsuarioFeignHandlerImp(usuarioFeignClient);
    }
    @Bean
    public IRestaurantePersistencePort restaurantePersistencePort(){
        return new RestauranteMysqlAdapter(restauranteRepository,restauranteEntityMapper);
    }
    @Bean
    public IPlatoServicePort platoServicePort(){
        return new PlatoUseCase(platoPersistencePort(),restaurantePersistencePort(),categoriaPersistencePort(),feignServicePort());
    }
    @Bean
    public IPlatoPersistencePort platoPersistencePort(){
        return new PlatoMysqlAdapter(platoRepository,platoEntityMapper);
    }
    @Bean
    public ICategoriaPersistencePort categoriaPersistencePort(){
        return new CategoriaMysqlAdapter(categoriaRepository,categoriaEntityMapper);
    }
}
