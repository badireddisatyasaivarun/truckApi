package com.TruckApi.TruckApi.Exception;

public class BusinessException extends RuntimeException {

	public BusinessException(String s) {
		super(BusinessException.generateMessage(s));
	}

	private static String generateMessage(String entity) {
		return "Error: " + entity;
	}



}
