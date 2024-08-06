package com.example.diodemeasurement.repository.measurement;

import com.example.diodemeasurement.model.measurement.Fitting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FittingRepository extends JpaRepository<Fitting, Long> {
}
