package com.example.diodemeasurement.controller;

import com.example.diodemeasurement.dto.measurement.MeasurementDTO;
import com.example.diodemeasurement.dto.measurement.SaveMeasurementRequest;
import com.example.diodemeasurement.model.measurement.Measurement;
import com.example.diodemeasurement.service.measurement.MeasurementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/measurements")
@AllArgsConstructor
public class MeasurementController {

		private final MeasurementService measurementService;
		private final ObjectMapper objectMapper;

		@PostMapping("/save")
		public ResponseEntity<String> saveMeasurement(@RequestParam("data") String data,
		                                              @RequestParam("file") MultipartFile file,
		                                              @AuthenticationPrincipal Jwt principal) {

				try{
						SaveMeasurementRequest saveMeasurementRequest = objectMapper.readValue(data, SaveMeasurementRequest.class);
						measurementService.saveMeasurement(saveMeasurementRequest, file, principal);
						return new ResponseEntity<>("Measurement saved successfully", HttpStatus.CREATED);
				} catch (IOException e) {
						return ResponseEntity.status(500).body("Failed to save measurement: " + e.getMessage());
				}

		}

		@DeleteMapping("/delete/{id}")
		public ResponseEntity<Measurement> deleteMeasurement(@PathVariable long id,
		                                                     @AuthenticationPrincipal Jwt principal) {
				Measurement measurement = measurementService.deleteMeasurementById(id, principal);
				if (measurement != null) {
						return new ResponseEntity<>(measurement, HttpStatus.OK);
				} else {
						return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
		}

		@GetMapping("/getMeasurements")
		public ResponseEntity<List<MeasurementDTO>> getAllMeasurementsByUser(@AuthenticationPrincipal Jwt principal) {
				List<MeasurementDTO> measurementDTOList = measurementService.getAllMeasurementsByUser(principal);
				if (!measurementDTOList.isEmpty()) {
						return new ResponseEntity<>(measurementDTOList, HttpStatus.OK);
				} else {
						return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
		}

}
