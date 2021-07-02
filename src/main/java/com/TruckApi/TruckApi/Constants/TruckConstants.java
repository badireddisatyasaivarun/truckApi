package com.TruckApi.TruckApi.Constants;

public class TruckConstants {

	// error and success messages
	public static final String IN_CORRECT_TRANSPORTER_ID = "Failed: Enter Correct Transporter Id";
	public static final String TRUCK_NO_IS_INVALID = "Failed: Enter Correct Truck Number";
	public static final String EXISTING_TRUCK_AND_TRANSPORTER = "Failed: TruckNo is already Associated with TransporterId";
	public static final String ADD_SUCCESS = "Truck details Added Succcessfully";
	public static final String UPDATE_SUCCESS = "Truck details Updated Succcessfully";
	public static final String ACCOUNT_NOT_FOUND_ERROR = "Failed: Account not found";
	public static final String DELETE_SUCCESS = "Deleted Succcessfully";
	public static final String UPDATE_TRUCK_ID_ERROR = "Failed: Cannot update Truck Id";
	public static final String INVALID_NUMBER_OF_TYRES_ERROR = "Failed: invalid number of tyres";
	public static final String INVALID_TRUCK_TYPE_ERROR = "Failed: invalid tyres type";

	// truck data
	public static final String URI = "/truck";
	public static final String TRUCK_ID_URI = "/truck/truckId:62cc8557-52cd-4742-a11e-276cc7bec12e";
	public static final String TRUCK_ID = "truckId:62cc8557-52cd-4742-a11e-276cc7bec12e";
	public static final String TRANSPORTER_ID = "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67";
	public static final String TRUCK_NO = "AP 32 AD 2220";
	public static final String BASE_URI = "http://localhost:8080/truck";

	public static final String CHECK_TRUCK_NO = "^[A-Za-z]{2}[ -/]{0,1}[0-9]{1,2}[ -/]{0,1}(?:[A-Za-z]{0,1})[ -/]{0,1}[A-Za-z]{0,2}[ -/]{0,1}[0-9]{4}$";

	public static final long pageSize = 15;

}
