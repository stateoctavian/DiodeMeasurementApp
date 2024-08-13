package com.example.diodemeasurement.repository.measurement;

import com.example.diodemeasurement.model.User;
import com.example.diodemeasurement.model.measurement.InputFile;
import com.example.diodemeasurement.model.measurement.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

		Optional<Measurement> findByInputFile(InputFile inputFile);
		Optional<List<Measurement>> findByUser(User user);
}
