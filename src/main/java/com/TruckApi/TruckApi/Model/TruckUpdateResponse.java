package com.TruckApi.TruckApi.Model;

import com.TruckApi.TruckApi.Model.TruckRequest.TruckType;
import com.TruckApi.TruckApi.Model.TruckRequest.Tyres;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TruckUpdateResponse {
	
	
	private String status;
	private String transporterId;
	private String truckId;
}
