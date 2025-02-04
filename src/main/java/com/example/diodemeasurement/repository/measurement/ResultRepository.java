package com.example.diodemeasurement.repository.measurement;

import com.example.diodemeasurement.model.measurement.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<Measurement, Long> {
}
