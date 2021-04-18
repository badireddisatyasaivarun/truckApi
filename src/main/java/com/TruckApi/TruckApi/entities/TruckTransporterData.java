package com.TruckApi.TruckApi.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
public @Data class TruckTransporterData {

	@Id
	private UUID truckId;
	private UUID transporterId;
	
}
