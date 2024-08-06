package com.example.diodemeasurement.model.measurement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long id;
		private double phi;
		private double r2Score;
		private double MAEError;
		private double maxError;
		private int temperature;
		private double voltage;
		private double current;
		private double currentPred;

}
