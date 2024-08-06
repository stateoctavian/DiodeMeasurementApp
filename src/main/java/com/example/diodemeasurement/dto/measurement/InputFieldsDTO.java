package com.example.diodemeasurement.dto.measurement;


import com.example.diodemeasurement.model.measurement.ContactType;
import com.example.diodemeasurement.model.measurement.Fitting;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputFieldsDTO {

		private double an;
		private double diodeDiameter;
		private double contactArea;
		private double minCurrent;
		private double maxCurrent;
		private double minVoltage;
		private double maxVoltage;
		private ContactType contactType;
		private int numberOfDiodes;
		private FittingDTO fittingDTO;
}
