package com.TruckApi.TruckApi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.TruckApi.TruckApi.Constants.TruckConstants;
import com.TruckApi.TruckApi.Model.TruckCreateResponse;
import com.TruckApi.TruckApi.Model.TruckRequest;
import com.TruckApi.TruckApi.Model.TruckUpdateRequest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;

public class ApiTestRestAssured {

	
	 @BeforeAll
	    public static void setup() {
	        RestAssured.baseURI = TruckConstants.BASE_URI;
	        
	 }
	 
	@Test
	public void getTruckDataWithId() throws Exception {

		Response response = RestAssured.given().header("", "").header("accept", "application/json")
				.header("Content-Type", "application/json").get("/truck:98bc8bb5-448d-4fc6-9963-b604f8a1ddd7").then().extract().response();
		
		assertEquals(200, response.statusCode());
		assertEquals("ZP 90 XZ 2540", response.jsonPath().getString("truckNo"));
		
		
	}

	@Test
	public void getTruckDataWithParam() throws Exception {

		
		Response response = RestAssured.given().param("truckApproved", false).header("accept", "application/json")
				.header("Content-Type", "application/json").get().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(response.jsonPath().getList("$").size(), 2);

	}

	@Test
	public void addData() throws Exception {

		TruckRequest truckRequest = new TruckRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
				"IP 90 XZ 9540", true, "alpha", 50, "driver:0de885e0-5f43-4c68-8dde-b25464747865",
				TruckRequest.TruckType.OPEN_BODY_TRUCK, TruckRequest.Tyres.EIGHT_TYRES);

		String inputJson = mapToJson(truckRequest);

		
		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(TruckConstants.ADD_SUCCESS, response.jsonPath().getString("status"));

	}

	@Test
	public void updateData() throws Exception {

		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest("gamma", false, 1000, "driver:afdge", null,
				null);

		String inputJson = mapToJson(truckUpdateRequest);

		
		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/truck:4bf0bdc8-bc66-41d1-bccb-e7c70b78e2df").then()
				.extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(TruckConstants.UPDATE_SUCCESS, response.jsonPath().getString("status"));

	}

	@Test
	public void deleteData() throws Exception {

		
		Response response = RestAssured.given().header("", "").delete("/truck:4bf0bdc8-bc66-41d1-bccb-e7c70b78e2df")
				.then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(TruckConstants.DELETE_SUCCESS, response.jsonPath().getString("status"));

	}

	private static String mapToJson(Object object) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
		objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);

		return objectMapper.writeValueAsString(object);
	}

}
