package com.example.diodemeasurement.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "changePassword_token")
public class ChangePasswordToken {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		private String token;
		@OneToOne(fetch = FetchType.LAZY)
		private User user;
		private Instant expiryDate;

}
