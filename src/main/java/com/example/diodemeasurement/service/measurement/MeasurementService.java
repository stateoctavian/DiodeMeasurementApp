package com.example.diodemeasurement.service.measurement;

import com.example.diodemeasurement.dto.measurement.SaveMeasurementRequest;
import com.example.diodemeasurement.mapper.SaveMeasurementMapper;
import com.example.diodemeasurement.model.User;
import com.example.diodemeasurement.model.measurement.*;
import com.example.diodemeasurement.repository.UserRepository;
import com.example.diodemeasurement.repository.measurement.DiodeParamsRepository;
import com.example.diodemeasurement.repository.measurement.MeasurementRepository;
import com.example.diodemeasurement.repository.measurement.RsRepository;
import com.example.diodemeasurement.repository.measurement.SelectedTemperaturesRepository;
import com.example.diodemeasurement.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

}
