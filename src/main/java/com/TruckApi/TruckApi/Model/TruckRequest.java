package com.TruckApi.TruckApi.Model;

import java.util.UUID;

import lombok.Data;

public @Data class TruckRequest {

	private UUID transporter_id;
	private String truckNo;
	
}
