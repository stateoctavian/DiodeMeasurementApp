package com.example.diodemeasurement.controller;

import com.example.diodemeasurement.dto.*;
import com.example.diodemeasurement.service.AuthService;
import com.example.diodemeasurement.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private static final Logger logger = Logger.getLogger(AuthController.class);
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account activated successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try {
            AutheticationResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody ChangePasswordRequest changePasswordRequest){
        authService.forgotPassword(changePasswordRequest.getEmail());
        logger.info("User with email " + changePasswordRequest.getEmail()+ " forgot password.");
        return new ResponseEntity<>("Email sent to change password!", HttpStatus.CREATED);
    }

    @PostMapping("/changePassword/{token}")
    public ResponseEntity<String> changePassword(@PathVariable String token,
                                                 @RequestBody NewPasswordRequest newPasswordRequest){
        authService.changePassword(token, newPasswordRequest);
        return new ResponseEntity<>("Password successfully updated!", HttpStatus.CREATED);
    }

    @PostMapping("refresh/token")
    public AutheticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        return authService.refreshToken(refreshTokenRequest);

    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
            refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
            logger.info("User" + refreshTokenRequest.getUsername() + "logged out.");
            return new ResponseEntity<>("Refresh token deleted", HttpStatus.OK);

    }


}
