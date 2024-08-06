package com.example.diodemeasurement.dto.measurement;

import com.example.diodemeasurement.model.measurement.InputFields;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectedTemperaturesDTO {

		private int temperature;
		private InputFieldsDTO inputFields;
}
