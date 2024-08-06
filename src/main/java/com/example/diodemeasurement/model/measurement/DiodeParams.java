package com.example.diodemeasurement.model.measurement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DiodeParams {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long id;
		private double phi;
		private double p_eff;
		@Enumerated(EnumType.STRING)
		private RsInitializationType rsInitializationType;
		@ManyToOne(fetch = FetchType.LAZY)
		private InputFields inputFields;
}
