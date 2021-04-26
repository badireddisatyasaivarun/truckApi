package com.TruckApi.TruckApi.Model;

import java.util.UUID;

import lombok.Data;

public @Data class TruckUpdateRequest {
	private String imei;
	private Boolean approved;
	private String truckNo;
}
