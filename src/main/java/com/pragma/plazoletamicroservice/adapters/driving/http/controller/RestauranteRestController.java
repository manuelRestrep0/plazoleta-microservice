package com.pragma.plazoletamicroservice.adapters.driving.http.controller;

import com.pragma.plazoletamicroservice.adapters.driving.feign.client.UsuarioFeignClient;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.RestauranteRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.handlers.IRestauranteHandler;
import com.pragma.plazoletamicroservice.configuration.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/restaurante")
@RequiredArgsConstructor
public class RestauranteRestController {
    private final IRestauranteHandler restauranteHandler;
    private final UsuarioFeignClient usuarioFeignClient;

    @Operation(summary = "Agregar un nuevo restaurante",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Restaurante creado",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Propietario ya existente",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @PostMapping("/crear")
    public ResponseEntity<Map<String,String>> crearRestaurante(@Valid @RequestBody RestauranteRequestDto restauranteRequestDto){
        //ROL DE ADMINISTRADOR.
        restauranteHandler.crearRestaurante(restauranteRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY,Constants.CREACION_EXITOSA_RESTAURANTE)
        );
    }
    @GetMapping("/prueba/{token}")
    public ResponseEntity<String> pruebaId(@PathVariable("token") String token){
        String response = usuarioFeignClient.idUsuario(token);
        return ResponseEntity.ok().body(response);
    }



}
