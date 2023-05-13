package com.pragma.plazoletamicroservice.adapters.driving.http.controller;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.ModificarPlatoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.PlatoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.handlers.IPlatoHandler;
import com.pragma.plazoletamicroservice.configuration.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/plato")
@RequiredArgsConstructor
public class PlatoRestController {

    private final IPlatoHandler platoHandler;


    @Operation(summary = "Agregar un nuevo plato",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Plato registrado!",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "400", description = "Mala solicitud de registro, por favor verifique los datos",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @PostMapping("/agregar-plato")
    public ResponseEntity<Map<String,String>> crearPlato(@Valid @RequestBody PlatoRequestDto platoRequestDto){
        //ROL PROPIETARIO
        platoHandler.crearPlato(platoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY,Constants.PLATO_CREADO)
        );
    }

    @Operation(summary = "Modificar un plato",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Plato modificado!",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "400", description = "Mala solicitud de modificacion, por favor verifique los datos",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @PatchMapping ("/modificar-plato")
    public ResponseEntity<Map<String,String>> modificarPlato(@Valid @RequestBody ModificarPlatoRequestDto modificarPlatoRequestDto){
        platoHandler.modificarPlato(modificarPlatoRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY,Constants.PLATO_MODIFICADO)
        );
    }
}
