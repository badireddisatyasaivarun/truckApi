package com.TruckApi.TruckApi;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import com.TruckApi.TruckApi.Model.TruckRequest;
import com.TruckApi.TruckApi.Model.TruckUpdateRequest;
import com.TruckApi.TruckApi.Model.TruckUpdateResponse;
import com.TruckApi.TruckApi.Service.TruckServiceImpl;
import com.TruckApi.TruckApi.entities.TruckData;
import com.TruckApi.TruckApi.entities.TruckTransporterData;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value=TruckController.class)
class TestTruckController {

	@Autowired
	private TruckController truckController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private TruckDao truckDao;
	
	@MockBean 
	private SecondTruckDao sTruckDao;
	
	@MockBean
	private TruckServiceImpl truckService;
	
	
	//private TruckConstants truckConstants = new TruckConstants();
	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
	@Test
	public void addDataSuccess() throws Exception {
		
		//When Everythimg Is Fine
		TruckRequest truckRequest = new TruckRequest();
		TruckCreateResponse truckCreateResponse = new TruckCreateResponse();
		TruckData truckData = new TruckData();
		
		
		truckRequest.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckRequest.setTruckNo("He 23 re 4444");
		truckRequest.setDriverId("driver:abcd");
		truckRequest.setImei("alpha");
		truckRequest.setPassingWeight(50);
		
	
		truckCreateResponse.setStatus(TruckConstants.success);
	
		
		String inputJson = this.mapToJson(truckRequest) ;
		
	     String expectedJson = this.mapToJson(truckCreateResponse) ;
			
			when(truckService.addData(truckRequest)).thenReturn(truckCreateResponse);	
			
			
			String URI = "/truck";
//			RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
//					URI).accept(
//					MediaType.APPLICATION_JSON);
//			
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.post(URI)
					.accept(MediaType.APPLICATION_JSON).content(inputJson)
					.contentType(MediaType.APPLICATION_JSON);

			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			String outputInJson = result.getResponse().getContentAsString();
			//assertThat(outputInJson).isEqualTo(expectedJson);
		
			assertEquals(expectedJson,outputInJson);
			assertEquals(HttpStatus.OK.value(), response.getStatus());
		 
	 
	}
	
	
	
	@Test
	public void addDataFailed_invalidTransporterId() throws Exception{
		
		//When Transporter Id Is Null
		TruckRequest truckRequest = new TruckRequest();
		TruckCreateResponse truckCreateResponse = new TruckCreateResponse();

		// TruckData truckData=new TruckData();
		
		truckRequest.setTransporterId(null);
		 truckRequest.setTruckNo("He 23 re 4444");
		 truckRequest.setDriverId("driver:123");
		 truckRequest.setPassingWeight(3);
		
	     truckCreateResponse.setTruckId(null);
	     truckCreateResponse.setStatus(TruckConstants.inCorrectTransporterId);
	     truckCreateResponse.setTransporterId(null);
	     
	     
	     String inputJson = this.mapToJson(truckRequest) ;
			
	     String expectedJson = this.mapToJson(truckCreateResponse) ;
			
			when(truckService.addData(truckRequest)).thenReturn(truckCreateResponse);	
			
			
			String URI = "/truck";

		
			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.post(URI)
					.accept(MediaType.APPLICATION_JSON).content(inputJson)
					.contentType(MediaType.APPLICATION_JSON);

			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			String outputInJson = result.getResponse().getContentAsString();
			//assertThat(outputInJson).isEqualTo(expectedJson);
		
			assertEquals(expectedJson,outputInJson);
			assertEquals(HttpStatus.OK.value(), response.getStatus());
		 
	
	}
	
	
	
	@Test
	public void addDataFailed_invalidTruckNo() throws Exception{
		
		//When TruckNo Is Not valid
	 
		 
		 
		 TruckRequest truckRequest = new TruckRequest();
			TruckCreateResponse truckCreateResponse = new TruckCreateResponse();

			 TruckData truckData=new TruckData();
			
			truckRequest.setTransporterId(null);
			 truckRequest.setTruckNo("He 23 re 444");
			 truckRequest.setDriverId("driver:123");
			 truckRequest.setPassingWeight(3);
	
			 truckCreateResponse.setTruckId(null);
		     truckCreateResponse.setStatus(TruckConstants.truckNoIsInvalid);
		     truckCreateResponse.setTransporterId(null);
		     
		     
		     String inputJson = this.mapToJson(truckRequest) ;
				
		     String expectedJson = this.mapToJson(truckCreateResponse) ;
				
				when(truckService.addData(truckRequest)).thenReturn(truckCreateResponse);	
				
				
				String URI = "/truck";
				
				RequestBuilder requestBuilder = MockMvcRequestBuilders
						.post(URI)
						.accept(MediaType.APPLICATION_JSON).content(inputJson)
						.contentType(MediaType.APPLICATION_JSON);

				MvcResult result = mockMvc.perform(requestBuilder).andReturn();
				MockHttpServletResponse response = result.getResponse();
				String outputInJson = result.getResponse().getContentAsString();
				//assertThat(outputInJson).isEqualTo(expectedJson);
			
				assertEquals(expectedJson,outputInJson);
				assertEquals(HttpStatus.OK.value(), response.getStatus());
			 
		 
		 
	}
	

	@Test
	public void getTruckDataWithIdSuccess() throws Exception
	{
		// get all Data
		TruckData truckData = new TruckData();
		
		//Case:1
		truckData.setTruckId("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e");
		truckData.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckData.setTruckApproved(null);
		truckData.setTruckNo("Ap32re5444");
		truckData.setDriverId("driver:11");
		truckData.setPassingWeight(300);
		
		
		when(truckService.getDataById("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e")).thenReturn(truckData);	
		
		
		String URI = "/truck/truckId:62cc8557-52cd-4742-a11e-276cc7bec12e";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				URI).accept(
				MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String expectedJson = this.mapToJson(truckData);
		String outputInJson = result.getResponse().getContentAsString();
		//assertThat(outputInJson).isEqualTo(expectedJson);
	
		assertEquals(expectedJson,outputInJson);

	
	}
	
	
	
//	
	@Test
	public void updateDataSuccess() throws Exception{
		
		//Updating passingweight Only 
		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest();
		TruckUpdateResponse response = new TruckUpdateResponse();
		TruckData truckData = new TruckData();
		
		TruckRequest truckRequest = new TruckRequest();
		
		
		String truckId = "truckId:62cc8557-52cd-4742-a11e-276cc7bec12e";	
		
		truckRequest.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckRequest.setTruckNo("He 23 re 4444");
		truckRequest.setDriverId("driver:abcd");
		truckRequest.setImei("alpha");
		truckRequest.setPassingWeight(50);
		
	truckData.setTruckId(truckId);
	truckData.setTransporterId(truckRequest.getTransporterId());
	truckData.setTruckNo(truckRequest.getTruckNo());
	truckData.setDriverId(truckRequest.getDriverId());
	truckData.setImei(truckRequest.getImei());
	truckData.setPassingWeight(truckRequest.getPassingWeight());
		
	when(truckDao.findByTruckId(truckId)).thenReturn(truckData);		
//TruckData truckData = new TruckData();

	truckUpdateRequest.setPassingWeight(30);
	
		
			response.setStatus(TruckConstants.updateSuccess);
			response.setTransporterId(truckData.getTransporterId());
			response.setTruckId(truckId);
			
	     String inputJson = this.mapToJson(truckUpdateRequest) ;
			
	     String expectedJson = this.mapToJson(response) ;
			
			when(truckService.updateData(truckId,truckUpdateRequest)).thenReturn(response);	
			
			
			String URI = "/truck/truckId:62cc8557-52cd-4742-a11e-276cc7bec12e";

			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.put(URI)
					.accept(MediaType.APPLICATION_JSON).content(inputJson)
					.contentType(MediaType.APPLICATION_JSON);

			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response1 = result.getResponse();
			String outputInJson = result.getResponse().getContentAsString();
			//assertThat(outputInJson).isEqualTo(expectedJson);
		
			assertEquals(expectedJson,outputInJson);
			assertEquals(HttpStatus.OK.value(), response1.getStatus());
		
	}

	@Test
	public void updateDataFailed_AccountNotFound() throws Exception{
		
		// Truck Data doesn't Exists with given TruckId
//		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest();
//		TruckUpdateResponse response = new TruckUpdateResponse();
//		TruckData truckData = new TruckData("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","AP 32 AD 2222",false,null, 0, null, null, null);
//		
//		String truckId = "truckId:62cc8557-52cd-4742-a11e-276cc7bec12e";
//		
//		when(truckDao.findByTruckId(truckId)).thenReturn(null);
//		
//		response.setStatus(TruckConstants.failure);
//		assertEquals(response,truckController.updateTruck(truckId, truckUpdateRequest));
//		
		
		
		
		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest();
		TruckUpdateResponse response = new TruckUpdateResponse();
		TruckData truckData = new TruckData();
		
		TruckRequest truckRequest = new TruckRequest();
		
		
		String truckId = "truckId:62cc8557-52cd-4742-a11e-276cc7bec12e";	
		String truckId1 = "truckId:62cc8557-52cd-4742-a11e-276cc7sdgde";	
		
		
		truckRequest.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckRequest.setTruckNo("He 23 re 4444");
		truckRequest.setDriverId("driver:abcd");
		truckRequest.setImei("alpha");
		truckRequest.setPassingWeight(50);
		
	truckData.setTruckId(truckId);
	truckData.setTransporterId(truckRequest.getTransporterId());
	truckData.setTruckNo(truckRequest.getTruckNo());
	truckData.setDriverId(truckRequest.getDriverId());
	truckData.setImei(truckRequest.getImei());
	truckData.setPassingWeight(truckRequest.getPassingWeight());
		
	when(truckDao.findByTruckId(truckId1)).thenReturn(truckData);		
//TruckData truckData = new TruckData();

	truckUpdateRequest.setPassingWeight(30);
	
		
		response.setStatus(TruckConstants.AccountNotFoundError);
//			response.setTransporterId(truckData.getTransporterId());
//			response.setTruckId(truckId);
			
	     String inputJson = this.mapToJson(truckUpdateRequest) ;
			
	     String expectedJson = this.mapToJson(response) ;
			
			when(truckService.updateData(truckId1,truckUpdateRequest)).thenReturn(response);	
			
			
			String URI = "/truck/truckId:62cc8557-52cd-4742-a11e-276cc7sdgde";

			RequestBuilder requestBuilder = MockMvcRequestBuilders
					.put(URI)
					.accept(MediaType.APPLICATION_JSON).content(inputJson)
					.contentType(MediaType.APPLICATION_JSON);

			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response1 = result.getResponse();
			String outputInJson = result.getResponse().getContentAsString();
			//assertThat(outputInJson).isEqualTo(expectedJson);
		
			assertEquals(expectedJson,outputInJson);
			
			System.out.println(expectedJson);
			System.out.println(outputInJson);
			//assertEquals(HttpStatus.OK.value(), response1.getStatus());
		
		
		
		
		
	}

//	@Test
//	public void getTruckDataPagableCase1Test(){
//		
//		//Getting All TruckData having Given TransporterId default PageNo=0 
//		Integer pageNo;
//		String transporterId;
//		Boolean approved;
//		Pageable p;
//		
//		TruckData t1 = new TruckData("id1","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2222",false,null, pageNo, transporterId, null, null);
//		TruckData t2 = new TruckData("id2","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2221",false,null, pageNo, transporterId, null, null);
//		TruckData t3 = new TruckData("id3","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2220",false,null, pageNo, transporterId, null, null);
//		TruckData t4 = new TruckData("id4","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2219",true,null, pageNo, transporterId, null, null);
//		TruckData t5 = new TruckData("id5","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68","Ap32ad2219",true,null, pageNo, transporterId, null, null);
//		TruckData t6 = new TruckData("id6","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68","Ap32ad2211",false,null, pageNo, transporterId, null, null);
//		
//		//Case:1
//		pageNo=null;
//		transporterId = "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67";
//		approved = null;
//		p = PageRequest.of(0,2);
//		when(truckDao.findByTransporterId(transporterId,p)).thenReturn(Stream.of(t1,t2).collect(Collectors.toList()));
//		assertEquals(2,truckController.getTruckDataPagable(pageNo,transporterId,approved, transporterId).size());
//		
//		pageNo=1;
//		p = PageRequest.of(1,2);
//		when(truckDao.findByTransporterId(transporterId,p)).thenReturn(Stream.of(t3,t4).collect(Collectors.toList()));
//		assertEquals(2,truckController.getTruckDataPagable(pageNo,transporterId,approved, transporterId).size());
//		
//	}
//	
//	
//	
//	@Test
//	public void getTruckDataPagableCase2Test(){
//		
//		//Getting All TruckData Which have Approved is True default PageNo=0;
//		Integer pageNo;
//		String transporterId;
//		Boolean approved;
//		Pageable p;
//		
//		TruckData t1 = new TruckData("id1","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2222",false,null);
//		TruckData t2 = new TruckData("id2","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2221",false,null);
//		TruckData t3 = new TruckData("id3","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2220",false,null);
//		TruckData t4 = new TruckData("id4","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2219",true,null);
//		TruckData t5 = new TruckData("id5","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68","Ap32ad2219",true,null);
//		TruckData t6 = new TruckData("id6","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68","Ap32ad2211",false,null);
//		
//		pageNo=0;
//		approved=true;
//		transporterId=null;
//		p = PageRequest.of(0,2);
//		when(truckDao.findByTruckApproved(approved,p)).thenReturn(Stream.of(t4,t5).collect(Collectors.toList()));
//		assertEquals(2,truckController.getTruckDataPagable(pageNo,transporterId,approved, truckId).size());
//	}
//	
//	
//	@Test
//	public void getTruckDataPagableCase3Test(){
//		
//		//Getting All TruckData with Approved true and With Given TransporterID;
//		Integer pageNo;
//		String transporterId;
//		Boolean approved;
//		Pageable p;
//		
//		TruckData t1 = new TruckData("id1","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2222",false,null);
//		TruckData t2 = new TruckData("id2","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2221",false,null);
//		TruckData t3 = new TruckData("id3","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2220",false,null);
//		TruckData t4 = new TruckData("id4","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2219",true,null);
//		TruckData t5 = new TruckData("id5","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68","Ap32ad2219",true,null);
//		TruckData t6 = new TruckData("id6","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68","Ap32ad2211",false,null);
//		
//		pageNo=0;
//		approved=true;
//		transporterId="transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68";
//		p = PageRequest.of(0,2);
//		when(truckDao.findByTransporterIdAndTruckApproved(transporterId,approved,p)).thenReturn(Stream.of(t5).collect(Collectors.toList()));
//		assertEquals(1,truckController.getTruckDataPagable(pageNo,transporterId,approved).size());
//	}
//	
//	
//	@Test
//	public void deleteTest(){
//		
//		//Deleting Both TruckData and TruckTranspoerterData with TruckId
//		TruckData truckData = new TruckData();
//		truckData.setTruckId("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e");
//		truckData.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
//		when(truckDao.findByTruckId("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e")).thenReturn(truckData);
//		
//		
//		TruckTransporterData truckTransporterData = new TruckTransporterData();
//		truckTransporterData.setTruckId("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e");
//        truckTransporterData.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
//        when(sTruckDao.findByTruckId("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e")).thenReturn(truckTransporterData);
//        
//        truckController.delete("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e");
//        verify(truckDao,times(1)).delete(truckData);
//		verify(sTruckDao,times(1)).delete(truckTransporterData);
//        
//	}
//	
	

}
