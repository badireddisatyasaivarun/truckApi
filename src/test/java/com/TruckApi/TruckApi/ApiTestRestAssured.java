package com.TruckApi.TruckApi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.TruckApi.TruckApi.Constants.TruckConstants;
import com.TruckApi.TruckApi.Model.TruckCreateResponse;
import com.TruckApi.TruckApi.Model.TruckRequest;
import com.TruckApi.TruckApi.Model.TruckUpdateRequest;
import com.TruckApi.TruckApi.entities.TruckData;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;

public class ApiTestRestAssured {

	private static String truckId1;
	private static String truckId2;

	@BeforeAll
	public static void setup() throws Exception {
		RestAssured.baseURI = TruckConstants.BASE_URI;

		TruckRequest truckRequest = new TruckRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
				"AA 00 AA 1111", "alpha", 50, "driver:0de885e0-5f43-4c68-8dde-b25464747865",
				20, (long) 40, TruckRequest.TruckType.OPEN_HALF_BODY);

		String inputJson = mapToJson(truckRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		TruckRequest truckRequest1 = new TruckRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
				"AB 00 AA 1111", "alpha", 50, "driver:0de885e0-5f43-4c68-8dde-b25464747865",
				20, (long) 40, TruckRequest.TruckType.OPEN_HALF_BODY);

		String inputJson1 = mapToJson(truckRequest1);

		Response response1 = RestAssured.given().header("", "").body(inputJson1).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(TruckConstants.ADD_SUCCESS, response.jsonPath().getString("status"));
		truckId1 = response.jsonPath().getString("truckId");

		assertEquals(200, response1.statusCode());
		assertEquals(TruckConstants.ADD_SUCCESS, response1.jsonPath().getString("status"));
		truckId2 = response1.jsonPath().getString("truckId");

	}

	@Test
	
	public void addDataFailed_truckNoAlreadyExisted() throws Exception {

		TruckRequest truckRequest = new TruckRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
				"AA 00 AA 1111", "alpha", 50, "driver:0de885e0-5f43-4c68-8dde-b25464747865",
				20,null, TruckRequest.TruckType.OPEN_HALF_BODY);

		String inputJson = mapToJson(truckRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(TruckConstants.EXISTING_TRUCK_AND_TRANSPORTER, response.jsonPath().getString("status"));

	}

	@Test
	public void addDataFailed_invalidTruckNo_null() throws Exception {

		TruckRequest truckRequest = new TruckRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
				null, "alpha", 50, "driver:0de885e0-5f43-4c68-8dde-b25464747865",
				20,null, TruckRequest.TruckType.OPEN_HALF_BODY);
		
		String inputJson = mapToJson(truckRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(TruckConstants.TRUCK_NO_IS_INVALID, response.jsonPath().getString("status"));

	}

	@Test
	public void addDataFailed_invalidTruckNo_notNull1() throws Exception {

		TruckRequest truckRequest = new TruckRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
				"A 00 AA 1111", "alpha", 50, "driver:0de885e0-5f43-4c68-8dde-b25464747865",
				20,null, TruckRequest.TruckType.OPEN_HALF_BODY);
		
		String inputJson = mapToJson(truckRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(TruckConstants.TRUCK_NO_IS_INVALID, response.jsonPath().getString("status"));

	}

	@Test
	public void addDataFailed_invalidTruckNo_notNull2() throws Exception {

		TruckRequest truckRequest = new TruckRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
				"AA 00 AA 111", "alpha", 50, "driver:0de885e0-5f43-4c68-8dde-b25464747865",
				20,null, TruckRequest.TruckType.OPEN_HALF_BODY);

		String inputJson = mapToJson(truckRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").post().then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(TruckConstants.TRUCK_NO_IS_INVALID, response.jsonPath().getString("status"));

	}

	@Test
	
	public void getTruckDataWithId() throws Exception {

		Response response = RestAssured.given().header("", "").header("accept", "application/json")
				.header("Content-Type", "application/json").get("/" + truckId1).then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals("AA 00 AA 1111", response.jsonPath().getString("truckNo"));

	}
	
	//wrong truckId

	@Test
	
	public void getTruckDataWithParam() throws Exception {

		Response response = RestAssured.given().header("accept", "application/json")
				.header("Content-Type", "application/json").get().then().extract().response();

		assertEquals(200, response.statusCode());
//		long dataList=response.jsonPath().getList("$").size();
//		long check=dataList%15;
		assertEquals(response.jsonPath().getList("$").size(), 15);

	}

	@Test
	
	public void updateData() throws Exception {

		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest(false, "gamma", 1000, "driver:afdge", null, null, null);

		String inputJson = mapToJson(truckUpdateRequest);

		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
				.header("Content-Type", "application/json").put("/" + truckId1).then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(TruckConstants.UPDATE_SUCCESS, response.jsonPath().getString("status"));

	}

	
	
	@Test
	
	public void deleteData() throws Exception {

		Response response = RestAssured.given().header("", "").delete("/" + truckId1).then().extract().response();

		Response response1 = RestAssured.given().header("", "").delete("/" + truckId2).then().extract().response();

		assertEquals(200, response.statusCode());
		assertEquals(TruckConstants.DELETE_SUCCESS, response.jsonPath().getString("status"));

		assertEquals(200, response1.statusCode());
		assertEquals(TruckConstants.DELETE_SUCCESS, response1.jsonPath().getString("status"));

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
