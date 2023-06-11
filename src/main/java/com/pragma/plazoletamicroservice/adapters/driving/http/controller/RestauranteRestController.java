package com.pragma.plazoletamicroservice.adapters.driving.http.controller;

import com.pragma.plazoletamicroservice.adapters.driving.feign.client.UsuarioFeignClient;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.RestauranteRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.response.RestauranteResponseDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.handlers.IRestauranteHandler;
import com.pragma.plazoletamicroservice.adapters.driving.http.utilidades.JwtUtilidades;
import com.pragma.plazoletamicroservice.configuration.Constants;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/restaurante")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
public class RestauranteRestController {
    private final IRestauranteHandler restauranteHandler;
    private final UsuarioFeignClient usuarioFeignClient;
    private final HttpServletRequest request;

    @Operation(summary = "Agregar un nuevo restaurante. Rol: PROPIETARIO",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Restaurante creado",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "409", description = "Restaurante ya existente",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @PostMapping("/crear")
    public ResponseEntity<Map<String,String>> crearRestaurante(@Valid @RequestBody RestauranteRequestDto restauranteRequestDto){
        String token = request.getHeader("Authorization");
        JwtUtilidades.extraerToken(token);
        restauranteHandler.crearRestaurante(restauranteRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY,Constants.CREACION_EXITOSA_RESTAURANTE)
        );
    }
    @Operation(summary = "Listar restaurantes alfabeticamente. Rol: CLIENTE",
              responses = {
        @ApiResponse(responseCode = "200", description = "Restaurantes listados",
                content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
        @ApiResponse(responseCode = "400", description = "Mala solicitud",
                content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))
    })
    @GetMapping("/listar/{elementos}")
    public ResponseEntity<Page<RestauranteResponseDto>> obtenerRestaurantes(@PathVariable("elementos") Integer elementos, @RequestParam("pagina")int pagina){
        Page<RestauranteResponseDto> response = restauranteHandler.obtenerRestaurantes(elementos, pagina);
        return ResponseEntity.ok().body(response);
    }
    @Hidden
    @GetMapping("/prueba/{token}")
    public ResponseEntity<String> pruebaId(@PathVariable("token") String token){
        String response = usuarioFeignClient.idUsuario(token);
        return ResponseEntity.ok().body(response);
    }
    @Hidden
    @PostMapping("/registrar-empleado-restaurante")
    public boolean registrarEmpleado(@RequestParam("idEmpleado")Long idEmpleado, @RequestParam("idPropietario")Long idPropieratio, @RequestParam("idRestaurante")Long idRestaurante){
        return restauranteHandler.registrarEmpleado(idEmpleado, idPropieratio, idRestaurante);
    }
    @Hidden
    @GetMapping("/validar-propietario-restaurante")
    public boolean validarPropietarioRestauranre(@RequestParam("idPropietario")Long idPropietario, @RequestParam("idRestaurante")Long idRestaurante){
        return restauranteHandler.validarPropietarioRestaurante(idPropietario, idRestaurante);
    }

}
