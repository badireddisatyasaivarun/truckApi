package com.TruckApi.TruckApi.Model;

import java.util.UUID;

import lombok.Data;

public @Data class TruckResponse {

	private String transporterId;
	private String truckId;
	private String truckNo;
	private Boolean approved;
	private String imei;

		
}
