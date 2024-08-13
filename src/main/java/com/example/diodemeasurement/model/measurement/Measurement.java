package com.example.diodemeasurement.model.measurement;

import com.example.diodemeasurement.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Measurement {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long id;
		private String shortname;
		@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
		private InputFile inputFile;
		@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
		private InputFields inputFields;
		@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
		private Result result;
		@ManyToOne(fetch = FetchType.EAGER)
		private User user;


}
