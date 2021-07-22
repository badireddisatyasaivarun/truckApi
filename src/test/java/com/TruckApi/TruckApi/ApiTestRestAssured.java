//package com.TruckApi.TruckApi;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//
//import com.TruckApi.TruckApi.Constants.TruckConstants;
//import com.TruckApi.TruckApi.Model.TruckRequest;
//import com.TruckApi.TruckApi.Model.TruckUpdateRequest;
//import com.TruckApi.TruckApi.entities.TruckData;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import io.restassured.RestAssured;
//import io.restassured.response.Response;
//
//@TestMethodOrder(OrderAnnotation.class)
//public class ApiTestRestAssured {
//
//	private static String truckId1;
//	private static String truckId2;
//
//	private static long pageNo_truckApproved_False = 0;
//	private static long cnt_truckApproved_False = 0;
//	private static long pageNo_truckApproved_True = 0;
//	private static long cnt_truckApproved_True = 0;
//	private static long cnt_TransporterId = 0;
//	private static long pageNo_transporterId = 0;
//	private static long pageNo_transporterId_truckApproved_False = 0;
//	private static long cnt_TransporterId_truckApproved_False = 0;
//	private static long pageNo_transporterId_truckApproved_True = 0;
//	private static long cnt_TransporterId_truckApproved_True = 0;
//
//	@BeforeAll
//	public static void setup() throws Exception {
//		RestAssured.baseURI = TruckConstants.BASE_URI;
//
//		Response response2;
//		pageNo_truckApproved_False = 0;
//		cnt_truckApproved_False = 0;
//		while (true) {
//			response2 = RestAssured.given().param("pageNo", pageNo_truckApproved_False).param("truckApproved", false)
//					.header("accept", "application/json").header("Content-Type", "application/json").get().then()
//					.extract().response();
//
//			cnt_truckApproved_False += response2.jsonPath().getList("$").size();
//			if (response2.jsonPath().getList("$").size() != TruckConstants.pageSize)
//				break;
//
//			pageNo_truckApproved_False++;
//
//		}
//
//		Response response3;
//		pageNo_truckApproved_True = 0;
//		cnt_truckApproved_True = 0;
//		while (true) {
//			response3 = RestAssured.given().param("pageNo", pageNo_truckApproved_True).param("truckApproved", true)
//					.header("accept", "application/json").header("Content-Type", "application/json").get().then()
//					.extract().response();
//
//			cnt_truckApproved_True += response3.jsonPath().getList("$").size();
//			if (response3.jsonPath().getList("$").size() != TruckConstants.pageSize)
//				break;
//
//			pageNo_truckApproved_True++;
//
//		}
//
//		Response response4;
//		pageNo_transporterId = 0;
//		cnt_TransporterId = 0;
//		while (true) {
//			response4 = RestAssured.given().param("pageNo", pageNo_transporterId)
//					.param("transporterId", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69")
//					.header("accept", "application/json").header("Content-Type", "application/json").get().then()
//					.extract().response();
//
//			cnt_TransporterId += response4.jsonPath().getList("$").size();
//			if (response4.jsonPath().getList("$").size() != TruckConstants.pageSize)
//				break;
//
//			pageNo_transporterId++;
//
//		}
//
//		Response response5;
//		pageNo_transporterId_truckApproved_False = 0;
//		cnt_TransporterId_truckApproved_False = 0;
//		while (true) {
//			response5 = RestAssured.given().param("pageNo", pageNo_transporterId_truckApproved_False)
//					.param("transporterId", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69")
//					.param("truckApproved", false).header("accept", "application/json")
//					.header("Content-Type", "application/json").get().then().extract().response();
//
//			cnt_TransporterId_truckApproved_False += response5.jsonPath().getList("$").size();
//			if (response5.jsonPath().getList("$").size() != TruckConstants.pageSize)
//				break;
//
//			pageNo_transporterId_truckApproved_False++;
//
//		}
//
//		Response response6;
//		pageNo_transporterId_truckApproved_True = 0;
//		cnt_TransporterId_truckApproved_True = 0;
//		while (true) {
//			response6 = RestAssured.given().param("pageNo", pageNo_transporterId_truckApproved_True)
//					.param("transporterId", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69")
//					.param("truckApproved", true).header("accept", "application/json")
//					.header("Content-Type", "application/json").get().then().extract().response();
//
//			cnt_TransporterId_truckApproved_True += response6.jsonPath().getList("$").size();
//			if (response6.jsonPath().getList("$").size() != TruckConstants.pageSize)
//				break;
//
//			pageNo_transporterId_truckApproved_True++;
//
//		}
//
//		TruckRequest truckRequest = new TruckRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
//				"AA 00 AA 1111", "alpha", 50, "driver:0de885e0-5f43-4c68-8dde-b25464747865", 20, (long) 40,
//				TruckData.TruckType.OPEN_HALF_BODY);
//
//		String inputJson = mapToJson(truckRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").post().then().extract().response();
//
//		TruckRequest truckRequest1 = new TruckRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
//				"AB 00 AA 1111", "alpha", 50, "driver:0de885e0-5f43-4c68-8dde-b25464747865", 20, (long) 40,
//				TruckData.TruckType.OPEN_HALF_BODY);
//
//		String inputJson1 = mapToJson(truckRequest1);
//
//		Response response0 = RestAssured.given().header("", "").body(inputJson1).header("accept", "application/json")
//				.header("Content-Type", "application/json").post().then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(TruckConstants.ADD_SUCCESS, response.jsonPath().getString("status"));
//		truckId1 = response.jsonPath().getString("truckId");
//
//		assertEquals(200, response0.statusCode());
//		assertEquals(TruckConstants.ADD_SUCCESS, response0.jsonPath().getString("status"));
//		truckId2 = response0.jsonPath().getString("truckId");
//
//	}
//
//	@Test
//	@Order(1)
//	public void addDataFailed_invalidTransporterId() throws Exception {
//
//		TruckRequest truckRequest = new TruckRequest(null, "AZ 00 AA 1111", "alpha", 50,
//				"driver:0de885e0-5f43-4c68-8dde-b25464747865", 20, null, TruckData.TruckType.OPEN_HALF_BODY);
//
//		String inputJson = mapToJson(truckRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").post().then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(TruckConstants.IN_CORRECT_TRANSPORTER_ID, response.jsonPath().getString("status"));
//
//	}
//
//	@Test
//	@Order(1)
//	public void addDataFailed_sameTruckNo_differentTransporterId() throws Exception {
//
//		TruckRequest truckRequest = new TruckRequest("transporter:123", "AA 00 AA 1111", "alpha", 50,
//				"driver:0de885e0-5f43-4c68-8dde-b25464747865", 20, null, TruckData.TruckType.OPEN_HALF_BODY);
//
//		String inputJson = mapToJson(truckRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").post().then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(TruckConstants.ADD_SUCCESS, response.jsonPath().getString("status"));
//		String id = response.jsonPath().getString("truckId");
//
//		Response response1 = RestAssured.given().header("", "").delete("/" + id).then().extract().response();
//
//		assertEquals(200, response1.statusCode());
//		assertEquals(TruckConstants.DELETE_SUCCESS, response1.jsonPath().getString("status"));
//	}
//
//	@Test
//	@Order(2)
//	public void addDataFailed_truckNoAlreadyExisted() throws Exception {
//
//		TruckRequest truckRequest = new TruckRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
//				"AA 00 AA 1111", "alpha", 50, "driver:0de885e0-5f43-4c68-8dde-b25464747865", 20, null,
//				TruckData.TruckType.OPEN_HALF_BODY);
//
//		String inputJson = mapToJson(truckRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").post().then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(TruckConstants.EXISTING_TRUCK_AND_TRANSPORTER, response.jsonPath().getString("status"));
//
//	}
//
//	@Test
//	@Order(3)
//	public void addDataFailed_invalidTruckNo_null() throws Exception {
//
//		TruckRequest truckRequest = new TruckRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", null,
//				"alpha", 50, "driver:0de885e0-5f43-4c68-8dde-b25464747865", 20, null,
//				TruckData.TruckType.OPEN_HALF_BODY);
//
//		String inputJson = mapToJson(truckRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").post().then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(TruckConstants.TRUCK_NO_IS_INVALID, response.jsonPath().getString("status"));
//
//	}
//
//	@Test
//	@Order(4)
//	public void addDataFailed_invalidTruckNo_notNull1() throws Exception {
//
//		TruckRequest truckRequest = new TruckRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
//				"A 00 AA 1111", "alpha", 50, "driver:0de885e0-5f43-4c68-8dde-b25464747865", 20, null,
//				TruckData.TruckType.OPEN_HALF_BODY);
//
//		String inputJson = mapToJson(truckRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").post().then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(TruckConstants.TRUCK_NO_IS_INVALID, response.jsonPath().getString("status"));
//
//	}
//
//	@Test
//	@Order(5)
//	public void addDataFailed_invalidTruckNo_notNull2() throws Exception {
//
//		TruckRequest truckRequest = new TruckRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
//				"AA 00 AA 111", "alpha", 50, "driver:0de885e0-5f43-4c68-8dde-b25464747865", 20, null,
//				TruckData.TruckType.OPEN_HALF_BODY);
//
//		String inputJson = mapToJson(truckRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").post().then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(TruckConstants.TRUCK_NO_IS_INVALID, response.jsonPath().getString("status"));
//
//	}
//
//	@Test
//	@Order(6)
//	public void getTruckDataWithIdSuccess() throws Exception {
//
//		Response response = RestAssured.given().header("", "").header("accept", "application/json")
//				.header("Content-Type", "application/json").get("/" + truckId1).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
//				response.jsonPath().getString("transporterId"));
//
//		assertEquals("AA 00 AA 1111", response.jsonPath().getString("truckNo"));
//		assertEquals(false, Boolean.parseBoolean(response.jsonPath().getString("truckApproved")));
//		assertEquals("alpha", response.jsonPath().getString("imei"));
//		assertEquals(50, Long.parseLong(response.jsonPath().getString("passingWeight")));
//		assertEquals("driver:0de885e0-5f43-4c68-8dde-b25464747865", response.jsonPath().getString("driverId"));
//		assertEquals(20, Integer.parseInt(response.jsonPath().getString("tyres")));
//		assertEquals(40, Long.parseLong(response.jsonPath().getString("truckLength")));
//		assertEquals(String.valueOf(TruckData.TruckType.OPEN_HALF_BODY), response.jsonPath().getString("truckType"));
//
//	}
//
//	@Test
//	@Order(7)
//	public void getTruckDataWithIdFailed() throws Exception {
//
//		Response response = RestAssured.given().header("", "").header("accept", "application/json")
//				.header("Content-Type", "application/json").get("/truck:132536").then().extract().response();
//
//		assertEquals(response.asString(), "");
//	}
//
//	@Test
//	@Order(8)
//	public void getTruckDataWithParam_truckApproved_False() throws Exception {
//
//		long lastPageCount = cnt_truckApproved_False % TruckConstants.pageSize;
//		long page = pageNo_truckApproved_False;
//
//		if (lastPageCount >= TruckConstants.pageSize - 1)
//			page++;
//
//		Response response = RestAssured.given().param("pageNo", page).param("truckApproved", false)
//				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
//				.response();
//
//		assertEquals(200, response.statusCode());
//
//		if (lastPageCount <= TruckConstants.pageSize - 2) {
//
//			assertEquals(lastPageCount + 2, response.jsonPath().getList("$").size());
//
//		} else if (lastPageCount == TruckConstants.pageSize - 1) {
//			assertEquals(1, response.jsonPath().getList("$").size());
//		} else if (lastPageCount == TruckConstants.pageSize) {
//			assertEquals(2, response.jsonPath().getList("$").size());
//		}
//
//	}
//
//	@Test
//	@Order(8)
//	public void getTruckDataWithParam_transporterId() throws Exception {
//
//		long lastPageCount = cnt_TransporterId % TruckConstants.pageSize;
//		long page = pageNo_transporterId;
//
//		if (lastPageCount >= TruckConstants.pageSize - 1)
//			page++;
//
//		Response response = RestAssured.given().param("pageNo", page)
//				.param("transporterId", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69")
//				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
//				.response();
//
//		assertEquals(200, response.statusCode());
//
//		if (lastPageCount <= TruckConstants.pageSize - 2) {
//
//			assertEquals(lastPageCount + 2, response.jsonPath().getList("$").size());
//
//		} else if (lastPageCount == TruckConstants.pageSize - 1) {
//			assertEquals(1, response.jsonPath().getList("$").size());
//		} else if (lastPageCount == TruckConstants.pageSize) {
//			assertEquals(2, response.jsonPath().getList("$").size());
//		}
//
//	}
//
//	@Test
//	@Order(8)
//	public void getTruckDataWithParam_transporterId_truckApproved_False() throws Exception {
//
//		long lastPageCount = cnt_TransporterId_truckApproved_False % TruckConstants.pageSize;
//		long page = pageNo_transporterId_truckApproved_False;
//
//		if (lastPageCount >= TruckConstants.pageSize - 1)
//			page++;
//
//		Response response = RestAssured.given().param("pageNo", page)
//				.param("transporterId", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69")
//				.param("truckApproved", false).header("accept", "application/json")
//				.header("Content-Type", "application/json").get().then().extract().response();
//
//		assertEquals(200, response.statusCode());
//
//		if (lastPageCount <= TruckConstants.pageSize - 2) {
//
//			assertEquals(lastPageCount + 2, response.jsonPath().getList("$").size());
//
//		} else if (lastPageCount == TruckConstants.pageSize - 1) {
//			assertEquals(1, response.jsonPath().getList("$").size());
//		} else if (lastPageCount == TruckConstants.pageSize) {
//			assertEquals(2, response.jsonPath().getList("$").size());
//		}
//
//	}
//
//	@Test
//	@Order(9)
//	public void updateData1() throws Exception {
//
//		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest(true, null, 0, null, null, null, null);
//
//		String inputJson = mapToJson(truckUpdateRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").put("/" + truckId1).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(TruckConstants.UPDATE_SUCCESS, response.jsonPath().getString("status"));
//		assertEquals(true, Boolean.parseBoolean(response.jsonPath().getString("truckApproved")));
//		// assertEquals("alpha", response.jsonPath().getString("imei"));
//
//	}
//
//	@Test
//	@Order(9)
//	public void getTruckDataWithParam_truckApproved_True() throws Exception {
//
//		long lastPageCount = cnt_truckApproved_True % TruckConstants.pageSize;
//		long page = pageNo_truckApproved_True;
//
//		if (lastPageCount >= TruckConstants.pageSize)
//			page++;
//
//		Response response = RestAssured.given().param("pageNo", page).param("truckApproved", true)
//				.header("accept", "application/json").header("Content-Type", "application/json").get().then().extract()
//				.response();
//
//		assertEquals(200, response.statusCode());
//
//		if (lastPageCount <= TruckConstants.pageSize - 1) {
//
//			assertEquals(lastPageCount + 1, response.jsonPath().getList("$").size());
//
//		} else if (lastPageCount == TruckConstants.pageSize) {
//			assertEquals(1, response.jsonPath().getList("$").size());
//		}
//
//	}
//
//	@Test
//	@Order(9)
//	public void getTruckDataWithParam_transporterId_truckApproved_True() throws Exception {
//
//		long lastPageCount = cnt_TransporterId_truckApproved_True % TruckConstants.pageSize;
//		long page = pageNo_transporterId_truckApproved_True;
//
//		if (lastPageCount >= TruckConstants.pageSize)
//			page++;
//
//		Response response = RestAssured.given().param("pageNo", page)
//				.param("transporterId", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69")
//				.param("truckApproved", true).header("accept", "application/json")
//				.header("Content-Type", "application/json").get().then().extract().response();
//
//		assertEquals(200, response.statusCode());
//
//		if (lastPageCount <= TruckConstants.pageSize - 1) {
//
//			assertEquals(lastPageCount + 1, response.jsonPath().getList("$").size());
//
//		} else if (lastPageCount == TruckConstants.pageSize) {
//			assertEquals(1, response.jsonPath().getList("$").size());
//		}
//
//	}
//
//	@Test
//	@Order(10)
//	public void updateData2() throws Exception {
//
//		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest(null, "gamma", 0, null, null, null, null);
//
//		String inputJson = mapToJson(truckUpdateRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").put("/" + truckId1).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(TruckConstants.UPDATE_SUCCESS, response.jsonPath().getString("status"));
//		assertEquals(true, Boolean.parseBoolean(response.jsonPath().getString("truckApproved")));
//		assertEquals("gamma", response.jsonPath().getString("imei"));
//	}
//
//	@Test
//	@Order(11)
//	public void updateData3() throws Exception {
//
//		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest(null, null, 1000, null, null, null, null);
//
//		String inputJson = mapToJson(truckUpdateRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").put("/" + truckId1).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(TruckConstants.UPDATE_SUCCESS, response.jsonPath().getString("status"));
//		assertEquals(true, Boolean.parseBoolean(response.jsonPath().getString("truckApproved")));
//		assertEquals("gamma", response.jsonPath().getString("imei"));
//		assertEquals(1000, Long.parseLong(response.jsonPath().getString("passingWeight")));
//	}
//
//	@Test
//	@Order(12)
//	public void updateData4() throws Exception {
//
//		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest(null, null, 0, "driver:afdge", null, null, null);
//
//		String inputJson = mapToJson(truckUpdateRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").put("/" + truckId1).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(TruckConstants.UPDATE_SUCCESS, response.jsonPath().getString("status"));
//		assertEquals(true, Boolean.parseBoolean(response.jsonPath().getString("truckApproved")));
//		assertEquals("gamma", response.jsonPath().getString("imei"));
//		assertEquals(1000, Long.parseLong(response.jsonPath().getString("passingWeight")));
//		assertEquals("driver:afdge", response.jsonPath().getString("driverId"));
//
//	}
//
//	@Test
//	@Order(13)
//	public void updateData5() throws Exception {
//
//		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest(null, null, 0, null, 10, null, null);
//
//		String inputJson = mapToJson(truckUpdateRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").put("/" + truckId1).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(TruckConstants.UPDATE_SUCCESS, response.jsonPath().getString("status"));
//		assertEquals(true, Boolean.parseBoolean(response.jsonPath().getString("truckApproved")));
//		assertEquals("gamma", response.jsonPath().getString("imei"));
//		assertEquals(1000, Long.parseLong(response.jsonPath().getString("passingWeight")));
//		assertEquals("driver:afdge", response.jsonPath().getString("driverId"));
//		assertEquals(10, Integer.parseInt(response.jsonPath().getString("tyres")));
//	}
//
//	@Test
//	@Order(14)
//	public void updateData6() throws Exception {
//
//		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest(null, null, 0, null, null, (long) 20, null);
//
//		String inputJson = mapToJson(truckUpdateRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").put("/" + truckId1).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(TruckConstants.UPDATE_SUCCESS, response.jsonPath().getString("status"));
//		assertEquals(true, Boolean.parseBoolean(response.jsonPath().getString("truckApproved")));
//		assertEquals("gamma", response.jsonPath().getString("imei"));
//		assertEquals(1000, Long.parseLong(response.jsonPath().getString("passingWeight")));
//		assertEquals("driver:afdge", response.jsonPath().getString("driverId"));
//		assertEquals(10, Integer.parseInt(response.jsonPath().getString("tyres")));
//		assertEquals(20, Long.parseLong(response.jsonPath().getString("truckLength")));
//
//	}
//
//	@Test
//	@Order(15)
//	public void updateData7() throws Exception {
//
//		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest(null, null, 0, null, null, null,
//				TruckData.TruckType.OPEN_FULL_BODY);
//
//		String inputJson = mapToJson(truckUpdateRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").put("/" + truckId1).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(TruckConstants.UPDATE_SUCCESS, response.jsonPath().getString("status"));
//		assertEquals(true, Boolean.parseBoolean(response.jsonPath().getString("truckApproved")));
//		assertEquals("gamma", response.jsonPath().getString("imei"));
//		assertEquals(1000, Long.parseLong(response.jsonPath().getString("passingWeight")));
//		assertEquals("driver:afdge", response.jsonPath().getString("driverId"));
//		assertEquals(10, Integer.parseInt(response.jsonPath().getString("tyres")));
//		assertEquals(20, Long.parseLong(response.jsonPath().getString("truckLength")));
//		assertEquals(String.valueOf(TruckData.TruckType.OPEN_FULL_BODY), response.jsonPath().getString("truckType"));
//	}
//
//	@Test
//	@Order(16)
//	public void updateData8() throws Exception {
//
//		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest(null, "zeta", 100, null, null, null,
//				TruckData.TruckType.FULL_BODY_TRAILER);
//
//		String inputJson = mapToJson(truckUpdateRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").put("/" + truckId1).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(TruckConstants.UPDATE_SUCCESS, response.jsonPath().getString("status"));
//		assertEquals(true, Boolean.parseBoolean(response.jsonPath().getString("truckApproved")));
//		assertEquals("zeta", response.jsonPath().getString("imei"));
//		assertEquals(100, Long.parseLong(response.jsonPath().getString("passingWeight")));
//		assertEquals("driver:afdge", response.jsonPath().getString("driverId"));
//		assertEquals(10, Integer.parseInt(response.jsonPath().getString("tyres")));
//		assertEquals(20, Long.parseLong(response.jsonPath().getString("truckLength")));
//		assertEquals(String.valueOf(TruckData.TruckType.FULL_BODY_TRAILER), response.jsonPath().getString("truckType"));
//	}
//
//	@Test
//	@Order(17)
//	public void updateData9() throws Exception {
//
//		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest(null, null, 0, null, null, null,
//				TruckData.TruckType.OPEN_FULL_BODY);
//
//		String inputJson = mapToJson(truckUpdateRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").put("/" + truckId1).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(TruckConstants.UPDATE_SUCCESS, response.jsonPath().getString("status"));
//		assertEquals(true, Boolean.parseBoolean(response.jsonPath().getString("truckApproved")));
//		assertEquals("zeta", response.jsonPath().getString("imei"));
//		assertEquals(100, Long.parseLong(response.jsonPath().getString("passingWeight")));
//		assertEquals("driver:afdge", response.jsonPath().getString("driverId"));
//		assertEquals(10, Integer.parseInt(response.jsonPath().getString("tyres")));
//		assertEquals(20, Long.parseLong(response.jsonPath().getString("truckLength")));
//		assertEquals(String.valueOf(TruckData.TruckType.OPEN_FULL_BODY), response.jsonPath().getString("truckType"));
//	}
//
//	@Test
//	@Order(18)
//	public void updateDataFailed() throws Exception {
//
//		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest(null, null, 0, null, null, null,
//				TruckData.TruckType.OPEN_FULL_BODY);
//
//		String inputJson = mapToJson(truckUpdateRequest);
//
//		Response response = RestAssured.given().header("", "").body(inputJson).header("accept", "application/json")
//				.header("Content-Type", "application/json").put("/truck:132536").then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(TruckConstants.ACCOUNT_NOT_FOUND_ERROR, response.jsonPath().getString("status"));
//
//	}
//
//	@Test
//	@AfterAll
//	public static void deleteData() throws Exception {
//
//		Response response = RestAssured.given().header("", "").delete("/" + truckId1).then().extract().response();
//
//		Response response1 = RestAssured.given().header("", "").delete("/" + truckId2).then().extract().response();
//
//		assertEquals(200, response.statusCode());
//		assertEquals(TruckConstants.DELETE_SUCCESS, response.jsonPath().getString("status"));
//
//		assertEquals(200, response1.statusCode());
//		assertEquals(TruckConstants.DELETE_SUCCESS, response1.jsonPath().getString("status"));
//
//	}
//
//	private static String mapToJson(Object object) throws Exception {
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
//		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
//		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
//		objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
//
//		return objectMapper.writeValueAsString(object);
//	}
//
//}
