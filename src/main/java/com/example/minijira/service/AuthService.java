package com.example.minijira.service;

import com.example.minijira.dto.authDTO.LoginRequest;
import com.example.minijira.dto.authDTO.MeDTO;
import com.example.minijira.dto.authDTO.RegisterRequest;
import com.example.minijira.dto.authDTO.RegisterResponse;
import com.example.minijira.dto.authDTO.TokenResponseDto;

public interface AuthService {

    RegisterResponse register(RegisterRequest registerRequest);
    TokenResponseDto login(LoginRequest loginRequest);
    MeDTO me();

}
