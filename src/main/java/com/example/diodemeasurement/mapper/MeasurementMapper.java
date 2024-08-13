package com.example.diodemeasurement.mapper;

import com.example.diodemeasurement.dto.measurement.*;
import com.example.diodemeasurement.model.measurement.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MeasurementMapper {

		public List<MeasurementDTO> toMeasurementDTOList(Optional<List<Measurement>> measurements) {

				if(measurements.isEmpty()) return null;

				return measurements.orElse(List.of()).stream()
								.map(this::toMeasurementDTO)
								.collect(Collectors.toList());
		}


		public MeasurementDTO toMeasurementDTO(Measurement measurement) {
				MeasurementDTO measurementDTO = new MeasurementDTO();

				if(measurement == null) return null;

				measurementDTO.setResultDTO(toResultDTO(measurement.getResult()));
				measurementDTO.setInputFile(measurement.getInputFile());
				measurementDTO.setShortname(measurement.getShortname());

				if (measurement.getInputFields() != null) {
						measurementDTO.setInputFieldsDTO(toInputFieldsDTO(measurement.getInputFields()));
						measurementDTO.setDiodeParamsDTO(
										measurement.getInputFields().getDiodeParams().stream()
														.map(this::toDiodeParamsDTO)
														.collect(Collectors.toList())
						);
						measurementDTO.setSelectedTemperaturesDTO(
										measurement.getInputFields().getSelectedTemperatures().stream()
														.map(this::toSelectedTemperaturesDTO)
														.collect(Collectors.toList())
						);
				}


				return measurementDTO;
		}

		public SelectedTemperaturesDTO toSelectedTemperaturesDTO(SelectedTemperatures selectedTemperatures) {
				if (selectedTemperatures == null) return null;

				SelectedTemperaturesDTO selectedTemperaturesDTO = new SelectedTemperaturesDTO();
				selectedTemperaturesDTO.setTemperature(selectedTemperatures.getTemperature());

				// Mapping InputFields to InputFieldsDTO
				selectedTemperaturesDTO.setInputFields(toInputFieldsDTO(selectedTemperatures.getInputFields()));

				return selectedTemperaturesDTO;
		}

		public DiodeParamsDTO toDiodeParamsDTO(DiodeParams diodeParams) {
				if (diodeParams == null) return null;

				DiodeParamsDTO diodeParamsDTO = new DiodeParamsDTO();
				diodeParamsDTO.setPhi(diodeParams.getPhi());
				diodeParamsDTO.setP_eff(diodeParams.getP_eff());
				diodeParamsDTO.setRsInitializationType(diodeParams.getRsInitializationType());

				// Assuming RsList contains Integer values, adjust if needed
				List<Double> rsList = diodeParams.getRsList().stream()
								.map(Rs::getResistance)
								.collect(Collectors.toList());
				diodeParamsDTO.setRsList(rsList);

				return diodeParamsDTO;
		}

		public ResultDTO toResultDTO(Result result) {

				ResultDTO resultDTO = new ResultDTO();

				if(result == null) return null;

				resultDTO.setCurrent(result.getCurrent());
				resultDTO.setPhi(result.getPhi());
				resultDTO.setTemperature(result.getTemperature());
				resultDTO.setVoltage(result.getVoltage());
				resultDTO.setMAEError(result.getMAEError());
				resultDTO.setMaxError(result.getMaxError());
				resultDTO.setR2Score(result.getR2Score());
				resultDTO.setCurrentPred(result.getCurrentPred());

				return resultDTO;
		}

		public InputFieldsDTO toInputFieldsDTO(InputFields inputFields) {

				InputFieldsDTO inputFieldsDTO = new InputFieldsDTO();

				if(inputFields == null) return null;

				inputFieldsDTO.setFittingDTO(toFittingDTO(inputFields.getFitting()));
				inputFieldsDTO.setAn(inputFields.getAn());
				inputFieldsDTO.setContactArea(inputFields.getContactArea());
				inputFieldsDTO.setMaxCurrent(inputFields.getMaxCurrent());
				inputFieldsDTO.setMinCurrent(inputFields.getMinCurrent());
				inputFieldsDTO.setDiodeDiameter(inputFields.getDiodeDiameter());
				inputFieldsDTO.setContactType(inputFields.getContactType());
				inputFieldsDTO.setMinVoltage(inputFields.getMinVoltage());
				inputFieldsDTO.setMaxVoltage(inputFields.getMaxVoltage());
				inputFieldsDTO.setNumberOfDiodes(inputFieldsDTO.getNumberOfDiodes());

				return inputFieldsDTO;
		}

		public FittingDTO toFittingDTO(Fitting fitting) {

				FittingDTO fittingDTO = new FittingDTO();

				if(fitting == null) return null;

				fittingDTO.setLearningRate(fitting.getLearningRate());
				fittingDTO.setNumberOfSteps(fitting.getNumberOfSteps());
				fittingDTO.setHowOftenToPlot(fitting.getHowOftenToPlot());
				fittingDTO.setTypeOfDifferentiation(fitting.getTypeOfDifferentiation());

				return fittingDTO;
		}
}
