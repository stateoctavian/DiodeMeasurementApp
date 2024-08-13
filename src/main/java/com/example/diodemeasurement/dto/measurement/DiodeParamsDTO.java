package com.example.diodemeasurement.dto.measurement;

import com.example.diodemeasurement.model.measurement.InputFields;
import com.example.diodemeasurement.model.measurement.Rs;
import com.example.diodemeasurement.model.measurement.RsInitializationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiodeParamsDTO {

		private double phi;
		private double p_eff;
		private RsInitializationType rsInitializationType;
		private List<Double> rsList;
}
