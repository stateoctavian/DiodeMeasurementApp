package com.example.diodemeasurement.repository.measurement;

import com.example.diodemeasurement.model.measurement.InputFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InputFileRepository extends JpaRepository<InputFile, Long> {
}
