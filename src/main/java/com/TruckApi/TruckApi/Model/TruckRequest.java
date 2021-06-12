package com.TruckApi.TruckApi.Model;

<<<<<<< HEAD
import java.util.UUID;

import lombok.Data;

public @Data class TruckRequest {

	private String transporterId;
	private String truckNo;
	
=======
import java.util.HashMap;
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
public class TruckRequest {

	private String transporterId;
	private String truckNo;
	private Boolean truckApproved;

	private String imei;
	private  long passingWeight;
	private String driverId;
	private Integer tyres;

	@Enumerated(EnumType.STRING)
	private TruckType truckType;
	
	public enum TruckType {
		OPEN_HALF_BODY, OPEN_FULL_BODY, FLATBED, HALF_BODY_TRAILER, FULL_BODY_TRAILER, STANDARD_CONTAINER, HIGH_CUBE_CONTAINER;
	}

>>>>>>> 8033c3454448edc3d4dbe82633cd7fcff8b066a6
}
