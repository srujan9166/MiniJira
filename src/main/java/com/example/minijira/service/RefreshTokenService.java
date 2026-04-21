package com.example.minijira.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.minijira.exception.globalException.ResourceNotFoundException;
import com.example.minijira.model.RefreshToken;
import com.example.minijira.model.User;
import com.example.minijira.repository.RefreshTokenRepository;
import com.example.minijira.repository.UserRepository;
@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository repository;

    @Autowired
    private UserRepository userRepository;

    private static final long REFRESH_TOKEN_DURATION_SECONDS = 7 * 24 * 60 * 60;

    public RefreshToken createRefreshToken(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Optional: delete old tokens (recommended)
        // repository.deleteByUser(user);

        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plusSeconds(REFRESH_TOKEN_DURATION_SECONDS));

        return repository.save(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            repository.delete(token);
            throw new ResourceNotFoundException("Refresh token expired");
        }
        return token;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return repository.findByToken(token);
    }

    public void deleteByToken(String token) {
        repository.findByToken(token).ifPresent(repository::delete);
    }

    public void deleteByUser(User user) {
        repository.deleteByUser(user);
    }
}