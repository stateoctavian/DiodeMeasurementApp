package com.example.diodemeasurement.repository.measurement;

import com.example.diodemeasurement.model.measurement.Rs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RsRepository extends JpaRepository<Rs, Long> {
}
