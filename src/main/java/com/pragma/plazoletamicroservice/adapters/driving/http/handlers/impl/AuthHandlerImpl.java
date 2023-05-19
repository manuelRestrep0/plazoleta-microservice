package com.pragma.plazoletamicroservice.adapters.driving.http.handlers.impl;

import com.pragma.plazoletamicroservice.adapters.driving.http.handlers.IAuthHandler;
import com.pragma.plazoletamicroservice.domain.api.IAuthServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthHandlerImpl implements IAuthHandler {
    private final IAuthServicePort authServicePort;
    @Override
    public void guardarToken(String token) {
        authServicePort.guardarToken(token);
    }
}
