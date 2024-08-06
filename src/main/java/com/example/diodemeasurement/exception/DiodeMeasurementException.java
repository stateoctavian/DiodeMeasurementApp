package com.example.diodemeasurement.exception;

public class DiodeMeasurementException extends  RuntimeException{

    public DiodeMeasurementException(String message, Exception e) {
        super(message, e);
    }
    public DiodeMeasurementException(String message) {
        super(message);
    }
}
