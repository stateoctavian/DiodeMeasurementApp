package com.example.diodemeasurement.dto.measurement;

import com.example.diodemeasurement.model.measurement.ContactType;
import com.example.diodemeasurement.model.measurement.Fitting;
import com.example.diodemeasurement.model.measurement.SelectedTemperatures;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveMeasurementRequest {

		private String shortName;
		private double an;
		private double diodeDiameter;
		private double contactArea;
		private Set<Integer> selectedTemperatures;
		private double minCurrent;
		private double maxCurrent;
		private double minVoltage;
		private double maxVoltage;
		private ContactType contactType;
		private int numberOfDiodes;
		private List<DiodeParamsDTO> diodeParams;
		private FittingDTO fittingDTO;
		private ResultDTO resultDTO;
}
