package com.TruckApi.TruckApi.entities;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
public @Data class TruckData {

<<<<<<< HEAD
=======

>>>>>>> 2b02d77b3e4725b96b71c1f2ea692a7e9f105831
	@Id	
	private String truckId;
	
	private String transporterId;
	private String truckNo;
	private Boolean approved;
<<<<<<< HEAD
	private String imei;

	
	//constructor
	public TruckData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TruckData(String truckId, String transporterId, String truckNo, Boolean approved, String imei) {
		super();
		this.truckId = truckId;
		this.transporterId = transporterId;
		this.truckNo = truckNo;
		this.approved = approved;
		this.imei = imei;
	}
	
=======
	private String imei; 

	
>>>>>>> 2b02d77b3e4725b96b71c1f2ea692a7e9f105831
}

