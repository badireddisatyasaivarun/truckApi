package com.TruckApi.TruckApi;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

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
	
	

}
