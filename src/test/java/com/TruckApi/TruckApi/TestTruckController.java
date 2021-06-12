package com.TruckApi.TruckApi;

<<<<<<< HEAD

=======
>>>>>>> 8033c3454448edc3d4dbe82633cd7fcff8b066a6
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
<<<<<<< HEAD
=======
import java.util.Arrays;
import java.util.List;
>>>>>>> 8033c3454448edc3d4dbe82633cd7fcff8b066a6
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

<<<<<<< HEAD
=======
import org.junit.Ignore;
>>>>>>> 8033c3454448edc3d4dbe82633cd7fcff8b066a6
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
=======
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
>>>>>>> 8033c3454448edc3d4dbe82633cd7fcff8b066a6
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
<<<<<<< HEAD
import org.springframework.test.context.junit4.SpringRunner;
=======
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
>>>>>>> 8033c3454448edc3d4dbe82633cd7fcff8b066a6

import com.TruckApi.TruckApi.Constants.TruckConstants;
import com.TruckApi.TruckApi.Controller.TruckController;
import com.TruckApi.TruckApi.Dao.SecondTruckDao;
import com.TruckApi.TruckApi.Dao.TruckDao;
import com.TruckApi.TruckApi.Model.TruckCreateResponse;
<<<<<<< HEAD
=======
import com.TruckApi.TruckApi.Model.TruckDeleteResponse;
>>>>>>> 8033c3454448edc3d4dbe82633cd7fcff8b066a6
import com.TruckApi.TruckApi.Model.TruckRequest;
import com.TruckApi.TruckApi.Model.TruckUpdateRequest;
import com.TruckApi.TruckApi.Model.TruckUpdateResponse;
import com.TruckApi.TruckApi.Service.TruckServiceImpl;
import com.TruckApi.TruckApi.entities.TruckData;
import com.TruckApi.TruckApi.entities.TruckTransporterData;
<<<<<<< HEAD

@RunWith(SpringRunner.class)
@SpringBootTest
class TestTruckController {

	@Autowired
	private TruckController truckController;
	
	@MockBean
	private TruckDao truckDao;
	
	@MockBean 
	private SecondTruckDao sTruckDao;
	
	private TruckConstants truckConstants = new TruckConstants();
	
	@Test
	public void AddTruckCase1Test() {
		
		//When Everythimg Is Fine
		TruckRequest truckRequest = new TruckRequest();
		TruckCreateResponse truckCreateResponse = new TruckCreateResponse();
		TruckData truckData = new TruckData();
		
		
		truckRequest.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckRequest.setTruckNo("He 23 re 4444");
		
		
		truckCreateResponse.setId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckCreateResponse.setStatus(truckConstants.getSuccess());
		
	
		truckData.setApproved(false);
		truckData.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckData.setTruckId(null);
		truckData.setTruckNo("He 23 re 4444");
		ArrayList<TruckData> list=new ArrayList<TruckData>();
		
		when(truckDao.findByTransporterIdAndTruckNo(truckRequest.getTransporterId(),truckRequest.getTruckNo())).thenReturn(list);
        when(truckDao.save(truckData)).thenReturn(truckData);
        assertEquals(truckCreateResponse,truckController.addTruck(truckRequest));
           
	}
	
	
	
	@Test
	public void AddTruckCase2Test(){
		
		//When Transporter Id Is Null
		TruckRequest truckRequest = new TruckRequest();
		TruckCreateResponse truckCreateResponse = new TruckCreateResponse();
		
		 truckRequest.setTransporterId(null);
		 truckRequest.setTruckNo("He 23 re 4444");
		 
	     truckCreateResponse.setId(null);
	     truckCreateResponse.setStatus(truckConstants.getInCorrectTransporterId());
			
		 assertEquals(truckCreateResponse,truckController.addTruck(truckRequest));
		
	}
	
	
	
	@Test
	public void AddTruckCase3Test(){
		
		//When TruckNo Is NULL
		TruckRequest truckRequest = new TruckRequest();
		TruckCreateResponse truckCreateResponse = new TruckCreateResponse();
		
		truckRequest.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckRequest.setTruckNo("");
		
		truckCreateResponse.setId(null);
		truckCreateResponse.setStatus(truckConstants.getTruckNoIsInvalid());
	  	
	    assertEquals(truckCreateResponse,truckController.addTruck(truckRequest));
	}
	
	
	@Test
	public void AddTruckCase4Test(){
		
		// When TruckId is already present with the same TransporterId
		TruckRequest truckRequest = new TruckRequest();
		TruckCreateResponse truckCreateResponse = new TruckCreateResponse();
		TruckData t1,t2;
		
		truckRequest.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckRequest.setTruckNo("He 23 re 4444");
		
		t1 = new TruckData(null,"transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","He 23 re 4444",false,null);
		t2 = new TruckData(null,"transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","He 23 re 4445",false,null);
		
		truckCreateResponse.setId(null);
		truckCreateResponse.setStatus(truckConstants.getExistingTruckAndTransporter());
		
		when(truckDao.findByTransporterIdAndTruckNo(truckRequest.getTransporterId(),truckRequest.getTruckNo())).thenReturn(Stream.of(t1,t2).collect(Collectors.toList()));
		

	    assertEquals(truckCreateResponse,truckController.addTruck(truckRequest));
		
	}
	
	
	@Test
	public void getTruckDataWithIdTest()
	{
		// get all Data
		TruckData truckData = new TruckData();
		
		//Case:1
		truckData.setTruckId("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e");
		truckData.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckData.setApproved(null);
		truckData.setTruckNo("Ap32re5444");
		
		when(truckDao.findByTruckId(truckData.getTruckId())).thenReturn(truckData);	
		assertEquals(truckData,truckController.getTruckWithId("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e"));

	}
	
	
	
	
	@Test
	public void updateTruckCase1Test(){
		
		//Updating Approved Only 
		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest();
		TruckUpdateResponse response = new TruckUpdateResponse();
		TruckData truckData = new TruckData();
		String truckId = "truckId:62cc8557-52cd-4742-a11e-276cc7bec12e";
		
		truckUpdateRequest.setApproved(true);
		
		when(truckDao.save(truckData)).thenReturn(truckData);	
		when(truckDao.findByTruckId(truckId)).thenReturn(truckData);
		
		response.setStatus(truckConstants.getSuccess());

		assertEquals(response,truckController.updateTruck(truckId, truckUpdateRequest));

	}
	
	
	@Test
	public void updateTruckCase2Test(){
		
		//Updating Only IMEI
		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest();
		TruckUpdateResponse response = new TruckUpdateResponse();
		TruckData truckData = new TruckData("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","AP 32 AD 2222",false,null);
		String truckId = "truckId:62cc8557-52cd-4742-a11e-276cc7bec12e";
		
        truckUpdateRequest.setImei("dc2224c0-b24d-4604-91dd-a630d22dfe49");
		
        when(truckDao.findByTruckId(truckId)).thenReturn(truckData);
        truckData.setApproved(false);
        truckData.setImei("dc2224c0-b24d-4604-91dd-a630d22dfe49");
		when(truckDao.save(truckData)).thenReturn(truckData);	
		
		response.setStatus(truckConstants.getSuccess());

		assertEquals(response,truckController.updateTruck(truckId, truckUpdateRequest));
		
	}
	
	
	@Test
	public void updateTruckCase3Test(){
		
		//Updating Only TruckNo Success
		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest();
		TruckUpdateResponse response = new TruckUpdateResponse();
		TruckData truckData = new TruckData("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","AP 32 AD 2222",false,null);
		String truckId = "truckId:62cc8557-52cd-4742-a11e-276cc7bec12e";
		
		
        truckUpdateRequest.setTruckNo("AP 32 RE 4333");
		
        
        when(truckDao.findByTruckId(truckId)).thenReturn(truckData);
        truckData.setTruckNo("AP 32 RE 4333");
        truckData.setApproved(false);
		when(truckDao.save(truckData)).thenReturn(truckData);	
		
		
		response.setStatus(truckConstants.getSuccess());

		assertEquals(response,truckController.updateTruck(truckId, truckUpdateRequest));
	}
	
	
	
	@Test
	public void updateTruckCase4Test(){
		
		//Updating Only TruckNo having Null Value
		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest();
		TruckUpdateResponse response = new TruckUpdateResponse();
		TruckData truckData = new TruckData("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","AP 32 AD 2222",false,null);
		String truckId = "truckId:62cc8557-52cd-4742-a11e-276cc7bec12e";
		
		truckUpdateRequest.setTruckNo("");
		
		when(truckDao.findByTruckId(truckId)).thenReturn(truckData);
				
		response.setStatus(truckConstants.getTruckNoIsInvalid());

		assertEquals(response,truckController.updateTruck(truckId, truckUpdateRequest));
		
	}
	
	@Test
	public void updateTruckCase5Test(){
		
		//Updating Approved, Imei and TruckNo
		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest();
		TruckUpdateResponse response = new TruckUpdateResponse();
		TruckData truckData = new TruckData("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","AP 32 AD 2222",false,null);
		String truckId = "truckId:62cc8557-52cd-4742-a11e-276cc7bec12e";
		
		truckUpdateRequest.setApproved(true);
		truckUpdateRequest.setImei("dc2224c0-b24d-4604-91dd-a630d22dfe49");
		truckUpdateRequest.setTruckNo("AP 32 RE 4333");
		
		
		when(truckDao.findByTruckId(truckId)).thenReturn(truckData);
        truckData.setTruckNo("AP 32 RE 4333");
        truckData.setImei("dc2224c0-b24d-4604-91dd-a630d22dfe49");
        truckData.setApproved(true);
		when(truckDao.save(truckData)).thenReturn(truckData);
		
		response.setStatus(truckConstants.getSuccess());

		assertEquals(response,truckController.updateTruck(truckId, truckUpdateRequest));
		
	}
	
	@Test
	public void updateTruckCase6Test(){
		
		//Updating Approved, Imei and TruckNo but TruckNo is NULL
		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest();
		TruckUpdateResponse response = new TruckUpdateResponse();
		TruckData truckData = new TruckData("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","AP 32 AD 2222",false,null);
		String truckId = "truckId:62cc8557-52cd-4742-a11e-276cc7bec12e";
				
		truckUpdateRequest.setApproved(true);
		truckUpdateRequest.setImei("dc2224c0-b24d-4604-91dd-a630d22dfe49");
		truckUpdateRequest.setTruckNo("");
				
				
		when(truckDao.findByTruckId(truckId)).thenReturn(truckData);
		truckData.setImei("dc2224c0-b24d-4604-91dd-a630d22dfe49");
		truckData.setApproved(false);
		when(truckDao.save(truckData)).thenReturn(truckData);
				
		response.setStatus(truckConstants.getTruckNoIsInvalid());

	    assertEquals(response,truckController.updateTruck(truckId, truckUpdateRequest));
	}
	
	
	@Test
	public void updateTruckCase7Test(){
		
		// Truck Data doesn't Exists with given TruckId
		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest();
		TruckUpdateResponse response = new TruckUpdateResponse();
		TruckData truckData = new TruckData("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","AP 32 AD 2222",false,null);
		
		String truckId = "truckId:62cc8557-52cd-4742-a11e-276cc7bec12e";
		
		when(truckDao.findByTruckId(truckId)).thenReturn(null);
		
		response.setStatus(truckConstants.getFailure());
		assertEquals(response,truckController.updateTruck(truckId, truckUpdateRequest));
		
	}
	
	
	@Test
	public void updateTruckCase8Test(){
		
		// Handeling after Updated, existing two elements with same TruckId and TransporterId case
		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest();
		TruckUpdateResponse response = new TruckUpdateResponse();
		TruckData t1 = new TruckData("id1","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","AP 32 AD 2222",false,null);
		TruckData t2 = new TruckData("id2","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","AP 32 AD 2221",false,null);
		TruckData t3 = new TruckData("id3","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68","AP 32 AD 2221",false,null);
		
		String truckId = "id1";
		
        truckUpdateRequest.setTruckNo("AP-32-AD-2221");
		
        when(truckDao.findByTruckId(truckId)).thenReturn(t1);
        when(truckDao.findByTruckNo("AP 32 AD 2221")).thenReturn(Stream.of(t2,t3).collect(Collectors.toList()));
		
		response.setStatus(truckConstants.getUpExistingTruckAndTransporter());
		assertEquals(response,truckController.updateTruck(truckId, truckUpdateRequest));
	}
	
	
	
	@Test
	public void getTruckDataPagableCase1Test(){
		
		//Getting All TruckData having Given TransporterId default PageNo=0 
		Integer pageNo;
		String transporterId;
		Boolean approved;
		Pageable p;
		
		TruckData t1 = new TruckData("id1","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2222",false,null);
		TruckData t2 = new TruckData("id2","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2221",false,null);
		TruckData t3 = new TruckData("id3","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2220",false,null);
		TruckData t4 = new TruckData("id4","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2219",true,null);
		TruckData t5 = new TruckData("id5","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68","Ap32ad2219",true,null);
		TruckData t6 = new TruckData("id6","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68","Ap32ad2211",false,null);
		
		//Case:1
		pageNo=null;
		transporterId = "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67";
		approved = null;
		p = PageRequest.of(0,2);
		when(truckDao.findByTransporterId(transporterId,p)).thenReturn(Stream.of(t1,t2).collect(Collectors.toList()));
		assertEquals(2,truckController.getTruckDataPagable(pageNo,transporterId,approved).size());
		
		pageNo=1;
		p = PageRequest.of(1,2);
		when(truckDao.findByTransporterId(transporterId,p)).thenReturn(Stream.of(t3,t4).collect(Collectors.toList()));
		assertEquals(2,truckController.getTruckDataPagable(pageNo,transporterId,approved).size());
		
	}
	
	
	
	@Test
	public void getTruckDataPagableCase2Test(){
		
		//Getting All TruckData Which have Approved is True default PageNo=0;
		Integer pageNo;
		String transporterId;
		Boolean approved;
		Pageable p;
		
		TruckData t1 = new TruckData("id1","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2222",false,null);
		TruckData t2 = new TruckData("id2","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2221",false,null);
		TruckData t3 = new TruckData("id3","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2220",false,null);
		TruckData t4 = new TruckData("id4","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2219",true,null);
		TruckData t5 = new TruckData("id5","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68","Ap32ad2219",true,null);
		TruckData t6 = new TruckData("id6","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68","Ap32ad2211",false,null);
		
		pageNo=0;
		approved=true;
		transporterId=null;
		p = PageRequest.of(0,2);
		when(truckDao.findByApproved(approved,p)).thenReturn(Stream.of(t4,t5).collect(Collectors.toList()));
		assertEquals(2,truckController.getTruckDataPagable(pageNo,transporterId,approved).size());
	}
	
	
	@Test
	public void getTruckDataPagableCase3Test(){
		
		//Getting All TruckData with Approved true and With Given TransporterID;
		Integer pageNo;
		String transporterId;
		Boolean approved;
		Pageable p;
		
		TruckData t1 = new TruckData("id1","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2222",false,null);
		TruckData t2 = new TruckData("id2","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2221",false,null);
		TruckData t3 = new TruckData("id3","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2220",false,null);
		TruckData t4 = new TruckData("id4","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2219",true,null);
		TruckData t5 = new TruckData("id5","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68","Ap32ad2219",true,null);
		TruckData t6 = new TruckData("id6","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68","Ap32ad2211",false,null);
		
		pageNo=0;
		approved=true;
		transporterId="transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68";
		p = PageRequest.of(0,2);
		when(truckDao.findByTransporterIdAndApproved(transporterId,approved,p)).thenReturn(Stream.of(t5).collect(Collectors.toList()));
		assertEquals(1,truckController.getTruckDataPagable(pageNo,transporterId,approved).size());
	}
	
	
	@Test
	public void deleteTest(){
		
		//Deleting Both TruckData and TruckTranspoerterData with TruckId
		TruckData truckData = new TruckData();
		truckData.setTruckId("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e");
		truckData.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		when(truckDao.findByTruckId("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e")).thenReturn(truckData);
		
		
		TruckTransporterData truckTransporterData = new TruckTransporterData();
		truckTransporterData.setTruckId("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e");
        truckTransporterData.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
        when(sTruckDao.findByTruckId("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e")).thenReturn(truckTransporterData);
        
        truckController.delete("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e");
        verify(truckDao,times(1)).delete(truckData);
		verify(sTruckDao,times(1)).delete(truckTransporterData);
        
	}
	
	
=======
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
>>>>>>> 8033c3454448edc3d4dbe82633cd7fcff8b066a6

}
