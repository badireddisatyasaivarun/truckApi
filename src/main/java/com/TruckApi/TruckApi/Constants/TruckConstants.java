package com.TruckApi.TruckApi.Constants;

import lombok.Data;


public @Data class TruckConstants {

	private String inCorrectTransporterId = "Failed: Enter Correct Transporter Id";
	private String truckNoIsInvalid = "Failed: Enter Correct Truck Number";
	private String existingTruckAndTransporter = "Failed: TruckNo is already Associated with TransporterId";
	private String upExistingTruckAndTransporter = "Failed: After Updating, Two elements Exists with same TruckNo and TransporterId";
	private String success = "Success";
	private String failure = "Failed";
	
}
