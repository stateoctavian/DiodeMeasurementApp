package com.example.diodemeasurement.dto.measurement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {

		private double phi;
		private double r2Score;
		private double MAEError;
		private double maxError;
		private int temperature;
		private double voltage;
		private double current;
		private double currentPred;
}
