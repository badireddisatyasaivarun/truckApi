package com.TruckApi.TruckApi.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

	@Enumerated(EnumType.STRING)
	private TruckType truckType;

	@Enumerated(EnumType.STRING)
	private Tyres tyres;

	public enum TruckType {
		LCV, OPEN_BODY_TRUCK, CLOSED_CONTAINER, TRAILER, TANKER, TIPPER, OTHERS
	}

	public enum Tyres {
		SIX_TYRES, EIGHT_TYRES, OTHERS
	}

}
