package com.TruckApi.TruckApi;

import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.skyscreamer.jsonassert.JSONParser;

import com.TruckApi.TruckApi.Constants.TruckConstants;
import com.TruckApi.TruckApi.Model.TruckCreateResponse;
import com.TruckApi.TruckApi.Model.TruckRequest;
import com.TruckApi.TruckApi.Model.TruckUpdateRequest;
import com.TruckApi.TruckApi.entities.TruckData;
import com.TruckApi.TruckApi.entities.TruckData.Tyres;
import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.EnumDeserializer;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import java.util.concurrent.TimeUnit;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.RestAssured;

public class ApiTestUnirest {

	@Test
	public void getTruckData() throws Exception {

		// Setting system properties of ChromeDriver
		System.setProperty("webdriver.chrome.driver", "F:\\downloads\\chromedriver_win32\\chromedriver.exe");

		// Creating an object of ChromeDriver
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();

		// Deleting all the cookies
		driver.manage().deleteAllCookies();

		// Specifiying pageLoadTimeout and Implicit wait
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// launching the specified URL
		driver.get("http://localhost:8080/truck?transporterId=jhgj");

		HttpResponse<JsonNode> body = Unirest.get("http://localhost:8080/truck").queryString("transporterId","jhgj").asJson();

		assertEquals(body.getStatus(), 200);
	}

	@Test
	public void addData() throws Exception {

		
		TruckRequest truckRequest = new TruckRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
				"AP 90 YZ 2540", true, "alpha", 50, "driver:0de885e0-5f43-4c68-8dde-b25464747865",
				TruckRequest.TruckType.OPEN_BODY_TRUCK, TruckRequest.Tyres.EIGHT_TYRES);

		String inputJson = mapToJson(truckRequest);

		HttpResponse<JsonNode> response1 = Unirest.post("http://localhost:8080/truck")
				.header("accept", "application/json").header("Content-Type", "application/json").body(inputJson)
				.asJson();

		assertEquals(response1.getBody().toString().contains(TruckConstants.ADD_SUCCESS), true);

	}

	@Test
	public void updateData() throws Exception {

	

		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest("beta", false, 1000, "driver:abccde", null,
				null);

		String inputJson = mapToJson(truckUpdateRequest);

		HttpResponse<JsonNode> response1 = Unirest.put("http://localhost:8080/truck/{truckId}")
				.routeParam("truckId", "truck:45712111-b39c-4ed3-9fa2-669373de41cd")
				.header("accept", "application/json").header("Content-Type", "application/json").body(inputJson)
				.asJson();

		assertEquals(response1.getBody().toString().contains(TruckConstants.UPDATE_SUCCESS), true);

	}

	
	@Test
	public void deleteData() throws Exception {

		HttpResponse<JsonNode> response1 = Unirest.delete("http://localhost:8080/truck/{truckId}")
				.routeParam("truckId", "truck:45712111-b39c-4ed3-9fa2-669373de41cd")
				.asJson();

		assertEquals(response1.getBody().toString().contains(TruckConstants.DELETE_SUCCESS), true);

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
