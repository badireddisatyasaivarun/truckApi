package com.TruckApi.TruckApi.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public @Data class TruckTransporterData {

	@Id
	private String truckId;
	private String transporterId;

}
