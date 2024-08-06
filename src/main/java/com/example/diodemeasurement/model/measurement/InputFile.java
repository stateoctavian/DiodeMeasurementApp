package com.example.diodemeasurement.model.measurement;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InputFile {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long id;
		private String shortName;
		@Lob
		@JdbcTypeCode(SqlTypes.BLOB)
		private byte[] csvFile;
}
