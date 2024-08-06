package com.example.diodemeasurement.repository.measurement;

import com.example.diodemeasurement.model.measurement.InputFields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InputFieldsRepository extends JpaRepository<InputFields, Long> {
}
