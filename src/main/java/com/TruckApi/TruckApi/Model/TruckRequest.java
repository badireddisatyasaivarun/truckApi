package com.TruckApi.TruckApi.Model;

import org.hibernate.validator.constraints.NotBlank;

import com.TruckApi.TruckApi.entities.TruckData.TruckType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TruckRequest {

	@NotBlank(message = "Transporter Id can not be null")
	private String transporterId;
	@NotBlank(message = "Truck Number can not be null")
	private String truckNo;

	private String imei;
	private long passingWeight;
	private String driverId;
	private Integer tyres;
	private Long truckLength;

	private TruckType truckType;

}
