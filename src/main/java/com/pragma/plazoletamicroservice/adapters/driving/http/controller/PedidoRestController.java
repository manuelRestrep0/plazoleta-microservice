package com.pragma.plazoletamicroservice.adapters.driving.http.controller;

import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.AsignarPedidoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.request.PedidoRequestDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.dto.response.PedidoResponseDto;
import com.pragma.plazoletamicroservice.adapters.driving.http.handlers.IPedidoHandler;
import com.pragma.plazoletamicroservice.adapters.driving.http.utilidades.JwtUtilidades;
import com.pragma.plazoletamicroservice.configuration.Constants;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/pedido")
@RequiredArgsConstructor
@SecurityRequirement(name = "jwt")
public class PedidoRestController {
    private final IPedidoHandler pedidoHandler;
    private final HttpServletRequest request;
    private String AUTH = "Authorization";

    @Operation(summary = "Agregar un nuevo pedido. Rol: CLIENTE",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pledido registrado!",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "400", description = "Mala solicitud de registro, por favor verifique los datos",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @PostMapping("/generar-pedido")
    public ResponseEntity<Map<String,String>> generarPedido(@Valid @RequestBody PedidoRequestDto pedidoRequestDto){
        String token = request.getHeader(AUTH);
        JwtUtilidades.extraerToken(token);
        pedidoHandler.generarPedido(pedidoRequestDto.getIdRestaurante(),pedidoRequestDto.getPlatos());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY,Constants.PEDIDO_CREADO)
        );
    }
    @Operation(summary = "Listar pedidos. Rol: EMPLEADO",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedidos devueltos",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "400", description = "Mala solicitud de registro, por favor verifique los datos",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @GetMapping("/obtener-pedidos")
    public Page<PedidoResponseDto> obtenerPedidos(@RequestParam("idRestaurante") Long idRestaurante,
                                                  @RequestParam("estado") String estado,
                                                  @RequestParam("elementos") Integer elementos,
                                                  @RequestParam("pagina") Integer pagina){
        String token = request.getHeader(AUTH);
        JwtUtilidades.extraerToken(token);
        return pedidoHandler.obtenerPedidosPorEstado(idRestaurante,estado,elementos, pagina);
    }

    @Operation(summary = "Asignar pedidos. Rol: EMPLEADO",
            responses = {
                    @ApiResponse(responseCode = "200", description = "pedidos asignados",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "400", description = "Mala solicitud de registro, por favor verifique los datos",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @PatchMapping("/asignar-pedido")
    public ResponseEntity<Map<String,String>> asignarPedido(@RequestBody AsignarPedidoRequestDto asignarPedidoRequestDto){
        String token = request.getHeader(AUTH);
        JwtUtilidades.extraerToken(token);
        pedidoHandler.asignarPedidoEmpleado(asignarPedidoRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY,Constants.PEDIDO_ASIGNADO)
        );
    }
    @Operation(summary = "Marcar pedido como listo. Rol: EMPLEADO",
            responses = {
                    @ApiResponse(responseCode = "200", description = "pedido marcado",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "400", description = "Mala solicitud de registro, por favor verifique los datos",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @PatchMapping("/pedido-listo/{id}")
    public ResponseEntity<Map<String,String>> marcarPedidoListo(@PathVariable("id") Long id){
        String token = request.getHeader(AUTH);
        JwtUtilidades.extraerToken(token);
        pedidoHandler.marcarPedidoListo(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY,"Pedido Listo.")
        );
    }
    @Operation(summary = "Marcar pedido como entregado. Rol: EMPLEADO",
            responses = {
                    @ApiResponse(responseCode = "200", description = "pedido marcado",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "400", description = "Mala solicitud de registro, por favor verifique los datos",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @PatchMapping("/pedido-entregado")
    public ResponseEntity<Map<String, String>> marcarPerdidoEntregado(@RequestParam("idPedido") Long idPedido, @RequestParam("codigo") Integer codigo){
        String token = request.getHeader(AUTH);
        JwtUtilidades.extraerToken(token);
        pedidoHandler.marcarPedidoEntregado(idPedido, codigo);
        return ResponseEntity.status(HttpStatus.OK).body((
                Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY,"Pedido entregado")
                ));
    }
    @Operation(summary = "Cancelar pedido. Rol: CLIENTE",
            responses = {
                    @ApiResponse(responseCode = "200", description = "pedido cancelado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "400", description = "Mala solicitud de registro, por favor verifique los datos",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @PatchMapping("/cancelar-pedido")
    public ResponseEntity<Map<String, String>> cancelarPedido(@RequestParam("idPedido") Long idPedido){
        String token = request.getHeader(AUTH);
        JwtUtilidades.extraerToken(token);
        String respuesta = pedidoHandler.cancelarPedido(idPedido);
        return ResponseEntity.status(HttpStatus.OK).body((
                Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY,respuesta)
        ));
    }

}
