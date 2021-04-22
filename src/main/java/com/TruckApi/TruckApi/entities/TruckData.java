package com.TruckApi.TruckApi.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
public @Data class TruckData {


	@Id	
	private String truckId;
	
	private String transporterId;
	private String truckNo;
	private Boolean approved;
	private String imei; 

	
}

