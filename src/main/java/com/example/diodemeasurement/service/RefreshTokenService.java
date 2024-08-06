package com.example.diodemeasurement.service;


import com.example.diodemeasurement.exception.DiodeMeasurementException;
import com.example.diodemeasurement.model.RefreshToken;
import com.example.diodemeasurement.model.User;
import com.example.diodemeasurement.repository.RefreshTokenRepository;
import com.example.diodemeasurement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshToken generateRefreshToken(){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());
        refreshToken.setExpiryDate(Instant.now().plusSeconds(60*60*24*365));
        return refreshTokenRepository.save(refreshToken);
    }

    void validateRefreshToken(String token, String name){
        if(userRepository.findByName(name).isEmpty()) {
            throw new DiodeMeasurementException("User not found");
        }
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new DiodeMeasurementException("Invalid refresh token"));
        if(refreshToken.getExpiryDate().isBefore(Instant.now())) {
            throw new DiodeMeasurementException("Refresh token expired");
        }
    }

    public void deleteRefreshToken(String token){
        refreshTokenRepository.findByToken(token)
                        .orElseThrow(() -> new DiodeMeasurementException("Token already deleted"));
        refreshTokenRepository.deleteByToken(token);
    }
}
