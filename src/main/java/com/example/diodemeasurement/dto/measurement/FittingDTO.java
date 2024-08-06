package com.example.diodemeasurement.dto.measurement;

import com.example.diodemeasurement.model.measurement.TypeOfDifferentiation;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FittingDTO {

		private int numberOfSteps;
		private int howOftenToPlot;
		private double learningRate;
		private TypeOfDifferentiation typeOfDifferentiation;
}
