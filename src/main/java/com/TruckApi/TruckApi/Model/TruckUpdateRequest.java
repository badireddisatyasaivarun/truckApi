package com.TruckApi.TruckApi.Model;

import java.util.UUID;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.TruckApi.TruckApi.entities.TruckData.TruckType;
import com.TruckApi.TruckApi.entities.TruckData.Tyres;

import lombok.Data;

public @Data class TruckUpdateRequest {
	
	private String imei;
	private Boolean truckApproved;
	private  long passingWeight;
	private String driverId;	


	@Enumerated(EnumType.STRING)
	private TruckType truckType;
	

	@Enumerated(EnumType.STRING)
	private Tyres tyres;
	
	public enum TruckType {
	    LCV,
	    OPEN_BODY_TRUCK,
	    CLOSED_CONTAINER,
	    TRAILER,
	    TANKER,
	    TIPPER,
	    OTHERS
	  }
	
	public enum Tyres{
		SIX_TYRES,
		EIGHT_TYRES,
		OTHERS
	}
}
