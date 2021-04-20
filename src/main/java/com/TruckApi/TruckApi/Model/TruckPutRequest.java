package com.TruckApi.TruckApi.Model;

import java.util.UUID;

import lombok.Data;

public @Data class TruckPutRequest {

	private UUID imei;
	private Boolean approved;
	
}
