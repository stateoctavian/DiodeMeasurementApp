package com.example.diodemeasurement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewPasswordRequest {

		private String password;
		private String reconfirmedPassword;
}
