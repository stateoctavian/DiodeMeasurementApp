package com.example.diodemeasurement.repository;

import com.example.diodemeasurement.model.ChangePasswordToken;
import com.example.diodemeasurement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordTokenRepository extends JpaRepository<ChangePasswordToken, Long> {

		Optional<ChangePasswordToken> findByToken(String token);
		Optional<ChangePasswordToken> findByUser(User user);
}
