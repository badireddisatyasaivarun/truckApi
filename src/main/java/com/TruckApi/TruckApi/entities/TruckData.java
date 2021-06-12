package com.TruckApi.TruckApi.entities;

import java.util.UUID;

import javax.persistence.Entity;
<<<<<<< HEAD
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

=======
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

>>>>>>> 8033c3454448edc3d4dbe82633cd7fcff8b066a6
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public @Data class TruckData {

<<<<<<< HEAD
	@Id	
	private String truckId;
	
	private String transporterId;
	private String truckNo;
	private Boolean approved;
	private String imei;
}

=======
	@Id
	private String truckId;

	private String transporterId;
	private String truckNo;
	private Boolean truckApproved;
	private String imei;
	private long passingWeight;
	private String driverId;
	private Integer tyres;
	
	@Enumerated(EnumType.STRING)
	public TruckType truckType;

	@Enumerated(EnumType.STRING)
	public Tyres tyres;

	public enum TruckType {
		OPEN_HALF_BODY, OPEN_FULL_BODY, FLATBED, HALF_BODY_TRAILER, FULL_BODY_TRAILER, STANDARD_CONTAINER, HIGH_CUBE_CONTAINER;
	}

}
>>>>>>> 8033c3454448edc3d4dbe82633cd7fcff8b066a6
