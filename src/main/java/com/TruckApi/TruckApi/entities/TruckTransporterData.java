package com.TruckApi.TruckApi.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public @Data class TruckTransporterData {

	@Id
	private String truckId;
	@NotBlank(message = "Transporter Id can not be null")
	private String transporterId;


}
