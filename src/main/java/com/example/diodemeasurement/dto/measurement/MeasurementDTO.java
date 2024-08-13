package com.example.diodemeasurement.dto.measurement;

import com.example.diodemeasurement.model.measurement.InputFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementDTO {

		private String shortname;
		private InputFile inputFile;
		private InputFieldsDTO inputFieldsDTO;
		private ResultDTO resultDTO;
		private List<DiodeParamsDTO> diodeParamsDTO;
		private List<SelectedTemperaturesDTO> selectedTemperaturesDTO;
}
