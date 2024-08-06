package com.example.diodemeasurement.model.measurement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Rs {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long id;
		private double resistance;
		@ManyToOne(fetch = FetchType.LAZY)
		private DiodeParams diodeParams;
		@ManyToOne(fetch = FetchType.LAZY)
		private Result result;
}
