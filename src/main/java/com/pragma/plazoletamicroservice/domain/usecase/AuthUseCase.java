package com.pragma.plazoletamicroservice.domain.usecase;

import com.pragma.plazoletamicroservice.domain.api.IAuthServicePort;
import com.pragma.plazoletamicroservice.domain.utilidades.Token;

public class AuthUseCase implements IAuthServicePort{

    @Override
    public void guardarToken(String token) {
        Token.setToken(token);
    }
}
