package com.example.diodemeasurement.service;


import com.example.diodemeasurement.dto.*;
import com.example.diodemeasurement.exception.DiodeMeasurementException;
import com.example.diodemeasurement.exception.UserAlreadyExists;
import com.example.diodemeasurement.model.ChangePasswordToken;
import com.example.diodemeasurement.model.User;
import com.example.diodemeasurement.model.VerificationToken;
import com.example.diodemeasurement.repository.PasswordTokenRepository;
import com.example.diodemeasurement.repository.UserRepository;
import com.example.diodemeasurement.repository.VerificationTokenRepository;
import com.example.diodemeasurement.security.JwtProvider;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;


@Service
@Transactional
public class AuthService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final PasswordTokenRepository passwordTokenRepository;

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, MailService mailService, AuthenticationManager authenticationManager, JwtProvider jwtProvider, RefreshTokenService refreshTokenService, PasswordTokenRepository passwordTokenRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.refreshTokenService = refreshTokenService;
        this.passwordTokenRepository = passwordTokenRepository;
    }

    @Transactional
    public void signup(RegisterRequest registerRequest){

    if(userRepository.findByName(registerRequest.getUsername()).isPresent())
        throw new UserAlreadyExists("User with this username already exists!");
     if(userRepository.findByEmail(registerRequest.getEmail()).isPresent())
        throw new UserAlreadyExists("User with this email already exists!");
    User user = new User();
    user.setName(registerRequest.getUsername());
    user.setEmail(registerRequest.getEmail());
    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    user.setCreated(Instant.now());
    user.setEnabled(false);
    userRepository.save(user);
    String token = generateVerificationToken(user);
//    mailService.sendMail(new NotificationEmail("Please activate your account",
//            user.getEmail(),
//            "Thank you for signing up to Spring Reddit, " +
//                    "please click on the below url to activate your account : " +
//                    "http://localhost:8080/api/auth/accountVerification/" + token));
        mailService.sendMailNoTrapMail(user.getEmail(),
                "Please activate your account",
                "Thank you for signing up to Diode Measurement Application, " +
                    "please click on the below url to activate your account: " +
                    "http://localhost:8081/api/auth/accountVerification/" + token
                );
    }

    private String generateVerificationToken(User user){
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        //verificationToken.setExpiryDate(Instant.now().plusSeconds(60*60*24));
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    private String generateChangePasswordToken(User user){
        String token = UUID.randomUUID().toString();
        ChangePasswordToken changePasswordToken = new ChangePasswordToken();
        Optional<ChangePasswordToken> ifUserExist = passwordTokenRepository.findByUser(user);
        if(ifUserExist.isPresent()){
            changePasswordToken = ifUserExist.get();
            changePasswordToken.setToken(token);
        }
        else{
            changePasswordToken.setToken(token);
            changePasswordToken.setUser(user);
            passwordTokenRepository.save(changePasswordToken);
        }
        return token;
    }

    public void forgotPassword(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow( () -> new DiodeMeasurementException("User with this email does not exist."));
        String token = generateChangePasswordToken(user);
        mailService.sendMailNoTrapMail(user.getEmail(),
                "Forgot password",
                "Access this link to change your password: " +
                "http://localhost:4200/app-change-password/" + token);
    }

    public void changePassword(String token, NewPasswordRequest newPasswordRequest){
        ChangePasswordToken changePasswordToken = passwordTokenRepository.findByToken(token)
                .orElseThrow(() -> new DiodeMeasurementException("Change password option has expired."));
        User user = changePasswordToken.getUser();
        if(newPasswordRequest.getPassword().equals(newPasswordRequest.getReconfirmedPassword()))
            user.setPassword(passwordEncoder.encode(newPasswordRequest.getPassword()));
        else
            throw new DiodeMeasurementException("Passwords do not match, try again!");

    }


    public void verifyAccount(String token) {
        Optional<VerificationToken>  verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new DiodeMeasurementException("Invalid Token"));
//        if(verificationToken.get().getExpiryDate().truncatedTo(ChronoUnit.SECONDS).isBefore(Instant.now().truncatedTo(ChronoUnit.SECONDS))){
//            throw new DiodeMeasurementException("Activation link expired, please reinitialize the process again.");
//        }
        fetchUserAndEnable(verificationToken.get());
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
       String username = verificationToken.getUser().getName();
       User user = userRepository.findByName(username).orElseThrow(() -> new DiodeMeasurementException("User not found with name: " + username));
       user.setEnabled(true);
       userRepository.save(user);
    }

    public AutheticationResponse login(LoginRequest loginRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            String token = jwtProvider.generateToken(authenticate);
            return AutheticationResponse.builder()
                    .authenticationToken(token)
                    .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                    .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                    .username(loginRequest.getUsername())
                    .build();
        } catch (BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials or account not activated. Please check your email for activation.");
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred during login. Please try again.");
        }
    }

//    public User getCurrentUser(){
//        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//        Jwt principal = (Jwt) authentication.getPrincipal();
//        return userRepository.findByName(principal.getSubject())
//                .orElseThrow(()-> new UsernameNotFoundException("User not found with name: " + principal.getSubject()));
//    }

    public User getCurrentUser(Jwt jwt) {
        return userRepository.findByName(jwt.getSubject())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with name: " + jwt.getSubject()));
    }

    public AutheticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        User user = userRepository.findByName(refreshTokenRequest.getUsername())
                .orElseThrow(() ->  new UsernameNotFoundException("User not found with name: " + refreshTokenRequest.getUsername()));
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken(), user.getName());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        System.out.println("Refresh Token Request Received: " + refreshTokenRequest);
        return AutheticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }
}
