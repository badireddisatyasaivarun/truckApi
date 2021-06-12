package com.TruckApi.TruckApi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.TruckApi.TruckApi.Constants.TruckConstants;
import com.TruckApi.TruckApi.Controller.TruckController;
import com.TruckApi.TruckApi.Dao.SecondTruckDao;
import com.TruckApi.TruckApi.Dao.TruckDao;
import com.TruckApi.TruckApi.Model.TruckCreateResponse;
import com.TruckApi.TruckApi.Model.TruckDeleteResponse;
import com.TruckApi.TruckApi.Model.TruckRequest;
import com.TruckApi.TruckApi.Model.TruckUpdateRequest;
import com.TruckApi.TruckApi.Model.TruckUpdateResponse;
import com.TruckApi.TruckApi.Service.TruckServiceImpl;
import com.TruckApi.TruckApi.entities.TruckData;
import com.TruckApi.TruckApi.entities.TruckTransporterData;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TruckController.class)
class TestTruckController {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TruckDao truckDao;

	@MockBean
	private SecondTruckDao sTruckDao;

	@MockBean
	private TruckServiceImpl truckService;

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

	@Test
	public void addData() throws Exception {

		TruckRequest truckRequest = new TruckRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
				"AP 32 AD 2220", true, "alpha", 50, "driver:0de885e0-5f43-4c68-8dde-b25464747865",
				TruckRequest.TruckType.OPEN_BODY_TRUCK, TruckRequest.Tyres.EIGHT_TYRES);

		TruckCreateResponse truckCreateResponse = new TruckCreateResponse(TruckConstants.ADD_SUCCESS, null, null);

		when(truckService.addData(truckRequest)).thenReturn(truckCreateResponse);

		String inputJson = mapToJson(truckRequest);

		String expectedJson = mapToJson(truckCreateResponse);

		String URI = TruckConstants.URI;

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(inputJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String outputInJson = result.getResponse().getContentAsString();

		assertEquals(expectedJson, outputInJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	public void getTruckDataWithId() throws Exception {
		
		List<TruckData> listTruckData = createTruckData();

		when(truckService.getDataById(TruckConstants.TRUCK_ID)).thenReturn(listTruckData.get(0));

		String URI = TruckConstants.TRUCK_ID_URI;
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = mapToJson(listTruckData.get(0));
		String outputInJson = result.getResponse().getContentAsString();
		
		assertEquals(expectedJson, outputInJson);

	}

	@Test
	public void getTruckDataWithParameters() throws Exception {

		// This test case is not working. Not sure why...Nothing is coming as output....

		List<TruckData> listTruckData = createTruckData();

		when(truckService.getTruckDataPagableService(0, TruckConstants.TRANSPORTER_ID, null, null))
				.thenReturn(listTruckData.subList(3, 5));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(TruckConstants.URI)
				.param("transporterId", TruckConstants.TRANSPORTER_ID).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = mapToJson(listTruckData.subList(3, 5));
		String outputInJson = result.getResponse().getContentAsString();

		assertEquals(expectedJson, outputInJson);

	}

//	
	@Test
	public void updateData() throws Exception {

		List<TruckData> listTruckData = createTruckData();

		when(truckService.getDataById(TruckConstants.TRUCK_ID)).thenReturn(listTruckData.get(0));

		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest("beta", false, 1000, "driver:abccde", null,
				null);

		TruckUpdateResponse response = new TruckUpdateResponse(TruckConstants.UPDATE_SUCCESS,
				listTruckData.get(0).getTransporterId(), TruckConstants.TRUCK_ID);

		String inputJson = mapToJson(truckUpdateRequest);

		String expectedJson = mapToJson(response);

		when(truckService.updateData(TruckConstants.TRUCK_ID, truckUpdateRequest)).thenReturn(response);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(TruckConstants.TRUCK_ID_URI)
				.accept(MediaType.APPLICATION_JSON).content(inputJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response1 = result.getResponse();
		String outputInJson = result.getResponse().getContentAsString();
		// assertThat(outputInJson).isEqualTo(expectedJson);

		assertEquals(expectedJson, outputInJson);
		assertEquals(HttpStatus.OK.value(), response1.getStatus());

	}

	@Test
	public void deleteData() throws Exception {

		List<TruckData> listTruckData = createTruckData();

		when(truckService.getDataById(TruckConstants.TRUCK_ID)).thenReturn(listTruckData.get(0));

		TruckDeleteResponse response = new TruckDeleteResponse(TruckConstants.DELETE_SUCCESS);

		String expectedJson = mapToJson(response);

		when(truckService.deleteData(TruckConstants.TRUCK_ID)).thenReturn(response);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(TruckConstants.TRUCK_ID_URI)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response1 = result.getResponse();
		String outputInJson = result.getResponse().getContentAsString();
		// assertThat(outputInJson).isEqualTo(expectedJson);

		assertEquals(expectedJson, outputInJson);
		assertEquals(HttpStatus.OK.value(), response1.getStatus());

	}

	public List<TruckData> createTruckData() {
		List<TruckData> truckList = Arrays.asList(
				new TruckData(TruckConstants.TRUCK_ID, "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
						"AP 32 AD 2220", true, "alpha", 50, "driver:0de885e0-5f43-4c68-8dde-b25464747865",
						TruckData.TruckType.OPEN_BODY_TRUCK, TruckData.Tyres.EIGHT_TYRES),
				new TruckData("id1", null, "AP 32 AD 2226", true, null, 0, null, null, null),
				new TruckData("id2", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", null, true, null, 0, null,
						null, null),
				new TruckData("id3", TruckConstants.TRANSPORTER_ID, "AP 32 AD 2220", true, null, 0, null, null, null),
				new TruckData("id4", TruckConstants.TRANSPORTER_ID, "Ap32ad2219", true, null, 0, null, null, null),
				new TruckData("id5", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68", "A32ad2219", false, null, 0,
						null, null, null),
				new TruckData("id6", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68", "Ap32ad221", false, null, 0,
						null, null, null));

		return truckList;
	}

}
