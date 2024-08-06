package com.example.diodemeasurement.service;

import com.example.diodemeasurement.exception.DiodeMeasurementException;
import com.example.diodemeasurement.model.User;
import com.example.diodemeasurement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByName(username);
        User user = userOptional.orElseThrow(() -> new DiodeMeasurementException("No user with such name: " + username));
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
                user.isEnabled(), true, true, true, getAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}
