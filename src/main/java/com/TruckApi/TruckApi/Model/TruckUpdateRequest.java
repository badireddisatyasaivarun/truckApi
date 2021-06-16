package com.TruckApi.TruckApi.Model;

import java.util.UUID;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.TruckApi.TruckApi.entities.TruckData.TruckType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TruckUpdateRequest {

	private Boolean truckApproved;

	private String imei;
	private long passingWeight;
	private String driverId;
	private Integer tyres;
	private Long truckLength;

	private TruckType truckType;
}
