package com.TruckApi.TruckApi.Model;

import com.TruckApi.TruckApi.entities.TruckData.TruckType;

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

	private String truckNo;

	private Boolean truckApproved;
	private String imei;
	private long passingWeight;
	private String driverId;
	private Integer tyres;

	private Long truckLength;

	private TruckType truckType;

}
