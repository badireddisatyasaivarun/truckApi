package com.TruckApi.TruckApi.Model;

import java.util.UUID;

<<<<<<< HEAD
import lombok.Data;

public @Data class TruckCreateResponse {
	private String status;
	private String id;
}
=======
import com.TruckApi.TruckApi.Model.TruckRequest.TruckType;
//import com.TruckApi.TruckApi.Model.TruckRequest.Tyres;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TruckCreateResponse {
	
	private String status;
	private String transporterId;
	private String truckId;
	
	}
>>>>>>> 8033c3454448edc3d4dbe82633cd7fcff8b066a6
