package com.example.diodemeasurement.mapper;

import com.example.diodemeasurement.dto.measurement.DiodeParamsDTO;
import com.example.diodemeasurement.dto.measurement.FittingDTO;
import com.example.diodemeasurement.dto.measurement.SaveMeasurementRequest;
import com.example.diodemeasurement.model.User;
import com.example.diodemeasurement.model.measurement.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

		@Component
		public class SaveMeasurementMapper {
				public Measurement mapToMeasurement(SaveMeasurementRequest request, MultipartFile file, User user) throws IOException {
				Measurement measurement = new Measurement();
				measurement.setShortname(request.getShortName());
				measurement.setInputFile(mapToInputFile(file));
				measurement.setInputFields(mapToInputFields(request));
				measurement.setUser(user);
				measurement.setResult(mapToResult(request));
				return measurement;
		}

		private InputFile mapToInputFile(MultipartFile file) throws IOException {
				InputFile inputFile = new InputFile();
				inputFile.setShortName(file.getOriginalFilename());
				inputFile.setCsvFile(file.getBytes());
				return inputFile;
		}

		private Result mapToResult(SaveMeasurementRequest request) {

				Result result = new Result();
				result.setCurrent(request.getResultDTO().getCurrent());
				result.setVoltage(request.getResultDTO().getVoltage());
				result.setPhi(request.getResultDTO().getPhi());
				result.setTemperature(request.getResultDTO().getTemperature());
				result.setMAEError(request.getResultDTO().getMAEError());
				result.setMaxError(request.getResultDTO().getMaxError());
				result.setCurrentPred(request.getResultDTO().getCurrentPred());
				result.setR2Score(request.getResultDTO().getR2Score());

				return result;

		}

		private InputFields mapToInputFields(SaveMeasurementRequest request) {
				InputFields inputFields = new InputFields();
				inputFields.setAn(request.getAn());
				inputFields.setDiodeDiameter(request.getDiodeDiameter());
				inputFields.setContactArea(request.getContactArea());
				inputFields.setMinCurrent(request.getMinCurrent());
				inputFields.setMaxCurrent(request.getMaxCurrent());
				inputFields.setMinVoltage(request.getMinVoltage());
				inputFields.setMaxVoltage(request.getMaxVoltage());
				inputFields.setContactType(request.getContactType());
				inputFields.setNumberOfDiodes(request.getNumberOfDiodes());
				inputFields.setFitting(mapToFitting(request.getFittingDTO()));

				return inputFields;
		}

		public Set<SelectedTemperatures> mapToSelectedTemperatures(SaveMeasurementRequest request, InputFields inputFields) {
				return request.getSelectedTemperatures().stream()
								.map(temp -> {
										SelectedTemperatures selectedTemperature = new SelectedTemperatures();
										selectedTemperature.setTemperature(temp);
										selectedTemperature.setInputFields(inputFields);
										return selectedTemperature;
								}).collect(Collectors.toSet());
		}


		public List<DiodeParams> mapToDiodeParamsList(SaveMeasurementRequest request, InputFields inputFields) {
				return request.getDiodeParams().stream()
								.map(dto -> mapToDiodeParams(dto, inputFields))
								.collect(Collectors.toList());
		}

		private DiodeParams mapToDiodeParams(DiodeParamsDTO dto, InputFields inputFields) {
				DiodeParams diodeParams = new DiodeParams();
				diodeParams.setPhi(dto.getPhi());
				diodeParams.setP_eff(dto.getP_eff());
				diodeParams.setRsInitializationType(dto.getRsInitializationType());
				diodeParams.setInputFields(inputFields);

				return diodeParams;
		}

		public List<Rs> mapToRsList(SaveMeasurementRequest request, DiodeParams diodeParams, Result result) {

				return request.getDiodeParams().stream()
								.flatMap(diodeParamDTO ->
										diodeParamDTO.getRsList().stream()
														.map(res -> {
																Rs rs = new Rs();
																rs.setResistance(res);
																rs.setDiodeParams(diodeParams);
																rs.setResult(result);
																return rs;
														}))
										.collect(Collectors.toList());
		}

		private Fitting mapToFitting(FittingDTO fittingDTO) {
				Fitting fitting = new Fitting();
				fitting.setNumberOfSteps(fittingDTO.getNumberOfSteps());
				fitting.setHowOftenToPlot(fittingDTO.getHowOftenToPlot());
				fitting.setLearningRate(fittingDTO.getLearningRate());
				fitting.setTypeOfDifferentiation(fittingDTO.getTypeOfDifferentiation());
				return fitting;
		}



}
