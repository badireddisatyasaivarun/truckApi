package com.TruckApi.TruckApi.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public @Data class TruckData {

	@Id
	private String truckId;

	private String transporterId;
	private String truckNo;
	private Boolean truckApproved;
	private String imei;
	private long passingWeight;
	private String driverId;
	private Integer tyres;
	private Long truckLength;

	@Enumerated(EnumType.STRING)
	public TruckType truckType;

	public enum TruckType {
		OPEN_HALF_BODY, OPEN_FULL_BODY, FLATBED, HALF_BODY_TRAILER, FULL_BODY_TRAILER, STANDARD_CONTAINER,
		HIGH_CUBE_CONTAINER;
	}

}
