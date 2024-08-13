package com.example.diodemeasurement.repository.measurement;

import com.example.diodemeasurement.model.measurement.DiodeParams;
import com.example.diodemeasurement.model.measurement.InputFields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiodeParamsRepository extends JpaRepository<DiodeParams, Long> {

		List<DiodeParams> findByInputFields(InputFields inputFields);
}
