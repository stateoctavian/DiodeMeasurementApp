package com.example.diodemeasurement.service.measurement;

import com.example.diodemeasurement.dto.measurement.MeasurementDTO;
import com.example.diodemeasurement.dto.measurement.SaveMeasurementRequest;
import com.example.diodemeasurement.exception.DiodeMeasurementException;
import com.example.diodemeasurement.mapper.MeasurementMapper;
import com.example.diodemeasurement.mapper.SaveMeasurementMapper;
import com.example.diodemeasurement.model.User;
import com.example.diodemeasurement.model.measurement.*;
import com.example.diodemeasurement.repository.UserRepository;
import com.example.diodemeasurement.repository.measurement.*;
import com.example.diodemeasurement.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MeasurementService {

		private final SaveMeasurementMapper saveMeasurementMapper;
		private final UserRepository userRepository;
		private final MeasurementRepository measurementRepository;
		private final AuthService authService;
		private final SelectedTemperaturesRepository selectedTemperaturesRepository;
		private final DiodeParamsRepository diodeParamsRepository;
		private final RsRepository rsRepository;
		private final MeasurementMapper measurementMapper;

		public void saveMeasurement(SaveMeasurementRequest saveMeasurementRequest, MultipartFile file, Jwt principal) throws IOException {

				User user = authService.getCurrentUser(principal);

				Measurement measurement = saveMeasurementMapper.mapToMeasurement(saveMeasurementRequest, file, user);
				InputFields inputFields = measurement.getInputFields();
				Result result = measurement.getResult();

				user.getMeasurementList().add(measurement);
				measurementRepository.save(measurement);

				Set<SelectedTemperatures> selectedTemperatures = saveMeasurementMapper.mapToSelectedTemperatures(saveMeasurementRequest, inputFields);
				selectedTemperaturesRepository.saveAll(selectedTemperatures);

				List<DiodeParams> diodeParamsList = saveMeasurementMapper.mapToDiodeParamsList(saveMeasurementRequest, inputFields);
				diodeParamsRepository.saveAll(diodeParamsList);

				diodeParamsList.forEach(diodeParams -> {
						List<Rs> rsList = saveMeasurementMapper.mapToRsList(saveMeasurementRequest, diodeParams, result);
						rsRepository.saveAll(rsList);
				});

				userRepository.save(user);

		}

		@Transactional
		public Measurement deleteMeasurementById(long id, Jwt principal) {

				User user = authService.getCurrentUser(principal);
				Optional<List<Measurement>> userMeasurements = measurementRepository.findByUser(user);

				if(userMeasurements.isPresent()){
						for(Measurement measurement : userMeasurements.get()) {
								if(measurement.getId() == id){
										InputFields inputFields = measurement.getInputFields();
										if (inputFields != null) {
												List<DiodeParams> diodeParamsList = diodeParamsRepository.findByInputFields(inputFields);
												diodeParamsRepository.deleteAll(diodeParamsList);
										}
										measurementRepository.delete(measurement);
										return measurement;
								}
						}
				}
				return null;
		}

		//get all measurements by input file id on the logged in user.

		@Transactional
		public List<MeasurementDTO> getAllMeasurementsByUser(Jwt principal) {

				User user = authService.getCurrentUser(principal);
				Optional<List<Measurement>> userMeasurements = measurementRepository.findByUser(user);
				return measurementMapper.toMeasurementDTOList(userMeasurements);
		}
}

