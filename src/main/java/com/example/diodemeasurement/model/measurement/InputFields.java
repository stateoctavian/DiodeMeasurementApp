package com.example.diodemeasurement.model.measurement;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InputFields {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long id;
		private double an;
		private double diodeDiameter;
		private double contactArea;
		private double minCurrent;
		private double maxCurrent;
		private double minVoltage;
		private double maxVoltage;
		@Enumerated(EnumType.STRING)
		private ContactType contactType;
		private int numberOfDiodes;
		@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
		private Fitting fitting;


}
