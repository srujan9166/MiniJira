package com.example.minijira.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.minijira.dto.authDTO.ForgotRequest;
import com.example.minijira.dto.authDTO.LoginRequest;
import com.example.minijira.dto.authDTO.MeDTO;
import com.example.minijira.dto.authDTO.RegisterRequest;
import com.example.minijira.dto.authDTO.RegisterResponse;
import com.example.minijira.dto.authDTO.TokenResponseDto;
import com.example.minijira.model.RefreshToken;
import com.example.minijira.model.User;
import com.example.minijira.repository.UserRepository;
import com.example.minijira.service.AuthServiceImpl;
import com.example.minijira.service.CustomUserDetailsService;
import com.example.minijira.service.RefreshTokenService;
import com.example.minijira.util.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthServiceImpl authServiceImpl;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthController(AuthServiceImpl authServiceImpl , RefreshTokenService refreshTokenService,JwtUtil jwtUtil ,UserRepository userRepository,CustomUserDetailsService customUserDetailsService){
        this.authServiceImpl = authServiceImpl;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtil =jwtUtil;
        this.userRepository= userRepository;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?>  register(@Valid @RequestBody RegisterRequest registerRequest){
           RegisterResponse registerResponse= authServiceImpl.register(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenResponseDto>  login(@Valid @RequestBody LoginRequest loginRequest){
        TokenResponseDto token = authServiceImpl.login(loginRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
    }

    @PostMapping("/forgot")
    public ResponseEntity<?> forgot(@RequestBody ForgotRequest forgotRequest ){
        

         return ResponseEntity.status(HttpStatus.ACCEPTED).body( authServiceImpl.forgot(forgotRequest));
    }
    @GetMapping("/me")
    public ResponseEntity<MeDTO> me(){
      return ResponseEntity.status(HttpStatus.FOUND).body(authServiceImpl.me());
    }

 @PostMapping("/refresh")
public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {

    String requestToken = request.get("refreshToken");

    return refreshTokenService.findByToken(requestToken)
            .map(refreshTokenService::verifyExpiration)
            .map(token -> token.getUser())
            .map(user -> {

                UserDetails userDetails = customUserDetailsService
                        .loadUserByUsername(user.getUsername());

                String accessToken = jwtUtil.generateToken(user.getUsername());

                return ResponseEntity.ok(
                        new TokenResponseDto(accessToken, requestToken)
                );
            })
            .orElseThrow(() -> new RuntimeException("Invalid refresh token"));
}
}
