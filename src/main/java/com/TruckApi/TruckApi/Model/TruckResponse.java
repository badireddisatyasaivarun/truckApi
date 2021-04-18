package com.TruckApi.TruckApi.Model;

import java.util.UUID;

import lombok.Data;

public @Data class TruckResponse {

	private UUID transporterId;
	private UUID truckId;
	private String truckNo;
	private boolean approved;
	private UUID IMEI;

		
}
