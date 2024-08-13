package com.example.diodemeasurement.model.measurement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectedTemperatures {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long id;
		private int temperature;
		@ManyToOne(fetch = FetchType.EAGER)
		private InputFields inputFields;

}
