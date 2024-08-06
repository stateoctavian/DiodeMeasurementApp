package com.example.diodemeasurement.repository.measurement;

import com.example.diodemeasurement.model.measurement.SelectedTemperatures;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectedTemperaturesRepository extends JpaRepository<SelectedTemperatures, Long> {
}
