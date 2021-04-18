package com.TruckApi.TruckApi.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
public @Data class TruckData {

	
	@GeneratedValue
	@Id
	private UUID truckId;
	
	private UUID transporterId;
	private String truckNo;
	private boolean approved;
	private UUID Imei; 

	
}

