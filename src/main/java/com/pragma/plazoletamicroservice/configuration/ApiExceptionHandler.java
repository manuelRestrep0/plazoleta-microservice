package com.pragma.plazoletamicroservice.configuration;

import com.pragma.plazoletamicroservice.domain.exceptions.NitYaRegistradoException;
import com.pragma.plazoletamicroservice.domain.exceptions.CategoriaNoEncontradaException;
import com.pragma.plazoletamicroservice.domain.exceptions.ClientePedidoActivoException;
import com.pragma.plazoletamicroservice.domain.exceptions.NombreRestauranteMalFormatoException;
import com.pragma.plazoletamicroservice.domain.exceptions.PedidoEstadoNoValidoException;
import com.pragma.plazoletamicroservice.domain.exceptions.PlatoNoEncontradoException;
import com.pragma.plazoletamicroservice.domain.exceptions.PropietarioOtroRestauranteException;
import com.pragma.plazoletamicroservice.domain.exceptions.RestauranteNoEncontradoException;
import com.pragma.plazoletamicroservice.domain.exceptions.UsuarioNoAutorizadoException;
import com.pragma.plazoletamicroservice.domain.exceptions.UsuarioNoPropietarioException;
import feign.FeignException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {NombreRestauranteMalFormatoException.class,
                                NitYaRegistradoException.class,
                                UsuarioNoPropietarioException.class,
                                RestauranteNoEncontradoException.class,
                                PlatoNoEncontradoException.class,
                                CategoriaNoEncontradaException.class,
                                PropietarioOtroRestauranteException.class,
                                ClientePedidoActivoException.class,
                                PedidoEstadoNoValidoException.class})
    public ResponseEntity<Object> BadRequestExceptionHandler(RuntimeException ex){
        ApiException apiException = new ApiException(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value={UsuarioNoAutorizadoException.class})
    public ResponseEntity<Object> NoAutorizadoExceptionHandler(RuntimeException ex){
        ApiException apiException = new ApiException(
                ex.getMessage(),
                HttpStatus.METHOD_NOT_ALLOWED,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                errorMessages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            } else {
                errorMessages.add(error.getDefaultMessage());
            }
        }
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> handleFeignStatusException(FeignException e, HttpServletResponse response) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.FORBIDDEN,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, HttpStatus.FORBIDDEN);

    }

}
