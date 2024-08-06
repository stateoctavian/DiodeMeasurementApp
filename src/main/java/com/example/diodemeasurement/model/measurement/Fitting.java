package com.example.diodemeasurement.model.measurement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Fitting {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long id;
		private int numberOfSteps;
		private int howOftenToPlot;
		private double learningRate;
		@Enumerated(EnumType.STRING)
		private TypeOfDifferentiation typeOfDifferentiation;
}
