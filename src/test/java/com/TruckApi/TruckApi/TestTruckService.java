
package com.TruckApi.TruckApi;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

import org.junit.Ignore;
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
import com.TruckApi.TruckApi.Dao.SecondTruckDao;
import com.TruckApi.TruckApi.Dao.TruckDao;
import com.TruckApi.TruckApi.Model.TruckCreateResponse;
import com.TruckApi.TruckApi.Model.TruckDeleteResponse;
import com.TruckApi.TruckApi.Model.TruckRequest;
import com.TruckApi.TruckApi.Model.TruckUpdateRequest;
import com.TruckApi.TruckApi.Model.TruckUpdateRequest;
import com.TruckApi.TruckApi.Model.TruckUpdateResponse;
import com.TruckApi.TruckApi.Service.TruckService;
import com.TruckApi.TruckApi.Service.TruckServiceImpl;
import com.TruckApi.TruckApi.entities.TruckData;
import com.TruckApi.TruckApi.entities.TruckData.Tyres;
import com.TruckApi.TruckApi.entities.TruckData.TruckType;

import com.TruckApi.TruckApi.entities.TruckTransporterData;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTruckService {


	@Autowired
	private TruckServiceImpl truckService;
	
	@MockBean
	private TruckDao truckDao;
	
	@MockBean 
	private SecondTruckDao sTruckDao;
	
	//private TruckConstants truckConstants = new TruckConstants();
	

	@Test
	public void addDataSuccess() {
		
		//When Everythimg Is Fine
		TruckRequest truckRequest = new TruckRequest();
		TruckCreateResponse truckCreateResponse = new TruckCreateResponse();
		TruckData truckData = new TruckData();
		
		
		truckRequest.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckRequest.setTruckNo("He 23 re 4444");
		truckRequest.setDriverId("driver:abcd");
		truckRequest.setImei("alpha");
		truckRequest.setPassingWeight(50);
		
	
	truckData.setTransporterId(truckRequest.getTransporterId());
	truckData.setTruckNo(truckRequest.getTruckNo());
	truckData.setDriverId(truckRequest.getDriverId());
	truckData.setImei(truckRequest.getImei());
	truckData.setPassingWeight(truckRequest.getPassingWeight());
		
	
	when(truckDao.save(truckData)).thenReturn(truckData);
    
		truckCreateResponse.setStatus(TruckConstants.success);
		
		assertEquals(truckCreateResponse.getStatus(),truckService.addData(truckRequest).getStatus());
        
           
	}
	
	
	
	@Test
	public void addDataFailed_invalidTransporterId(){
		
		//When Transporter Id Is Null
		TruckRequest truckRequest = new TruckRequest();
		TruckCreateResponse truckCreateResponse = new TruckCreateResponse();
	TruckData truckData = new TruckData();
		
		truckRequest.setTransporterId(null);
		truckRequest.setTruckNo("AB 34 FC 3959");
		truckRequest.setDriverId("driver:abcd");
		truckRequest.setImei("alpha");
		truckRequest.setPassingWeight(50);
		
	
	truckData.setTransporterId(truckRequest.getTransporterId());
	truckData.setTruckNo(truckRequest.getTruckNo());
	truckData.setDriverId(truckRequest.getDriverId());
	truckData.setImei(truckRequest.getImei());
	truckData.setPassingWeight(truckRequest.getPassingWeight());
	
	when(truckDao.save(truckData)).thenReturn(truckData);
    
	     truckCreateResponse.setTruckId(null);
	     truckCreateResponse.setStatus(TruckConstants.inCorrectTransporterId);
	     
		 assertEquals(truckCreateResponse,truckService.addData(truckRequest));
	
	}
	
	
	
	@Test
	public void addDataFailed_invalidTruckNo(){
		
		//When TruckNo Is NULL
		TruckRequest truckRequest = new TruckRequest();
		TruckCreateResponse truckCreateResponse = new TruckCreateResponse();
		
		TruckData truckData = new TruckData();
		
		truckRequest.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckRequest.setTruckNo("");
		truckRequest.setDriverId("driver:abcd");
		truckRequest.setImei("alpha");
		truckRequest.setPassingWeight(50);
		
	
	truckData.setTransporterId(truckRequest.getTransporterId());
	truckData.setTruckNo(truckRequest.getTruckNo());
	truckData.setDriverId(truckRequest.getDriverId());
	truckData.setImei(truckRequest.getImei());
	truckData.setPassingWeight(truckRequest.getPassingWeight());
	
	when(truckDao.save(truckData)).thenReturn(truckData);
    
		truckCreateResponse.setTruckId(null);
		truckCreateResponse.setStatus(TruckConstants.truckNoIsInvalid);
		
		 assertEquals(truckCreateResponse,truckService.addData(truckRequest));
			
	}
	
	
	
	@Test
	public void addDataFailed_TruckNoAlreadyExisted(){
		
		// When TruckId is already present with the same TransporterId
	
		/*
		 * I am not sure for this test case......
		 */
	
		TruckRequest truckRequest = new TruckRequest();
		TruckCreateResponse truckCreateResponse = new TruckCreateResponse();
		TruckData t1,t2;

		truckRequest.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckRequest.setTruckNo("He 23 re 4444");

		t1 = new TruckData(null,"transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","He 23 re 4444",false,null, 0, null, null, null);
		t2 = new TruckData(null,"transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","He 23 re 4445",false,null, 0, null, null, null);

		//truckCreateResponse.setId(null);
		truckCreateResponse.setStatus(TruckConstants.existingTruckAndTransporter);

		when(truckDao.findByTransporterIdAndTruckNo(truckRequest.getTransporterId(),truckRequest.getTruckNo())).thenReturn(Stream.of(t1,t2).collect(Collectors.toList()));
	
	    assertEquals(truckCreateResponse,truckService.addData(truckRequest));
	}
	
	
	
	@Test
	public void getTruckDataWithIdSuccess()
	{
		// get all Data
		TruckData truckData = new TruckData();
		
		//Case:1
		truckData.setTruckId("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e");
		truckData.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckData.setTruckApproved(null);
		truckData.setTruckNo("Ap32re5444");
		
		when(truckDao.findByTruckId("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e")).thenReturn(truckData);	
		assertEquals(truckData,truckService.getDataById("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e"));
		
	}
	
	
	
	
	@Test
	public void updateSuccess(){
		
		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest();
		TruckUpdateResponse response = new TruckUpdateResponse();
		TruckRequest truckRequest = new TruckRequest();
		
		TruckData truckData = new TruckData();
			
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
		
			truckUpdateRequest.setPassingWeight(30);
			truckUpdateRequest.setDriverId("driver:100");
			truckUpdateRequest.setPassingWeight(100);
			truckUpdateRequest.setImei("beta");
			
			
			response.setStatus(TruckConstants.updateSuccess);
			response.setTransporterId(truckData.getTransporterId());
			response.setTruckId(truckId);
		

		assertEquals(response,truckService.updateData(truckId, truckUpdateRequest));

	}
	
	
//	@Test
//	public void updateTruckCase2Test(){
//		
//		//Updating Only IMEI
//		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest();
//		TruckUpdateResponse response = new TruckUpdateResponse();
//		TruckRequest truckRequest = new TruckRequest();
//		
//		TruckData truckData = new TruckData();
//			
//		String truckId = "truckId:62cc8557-52cd-4742-a11e-276cc7bec12e";	
//				
//				truckRequest.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
//				truckRequest.setTruckNo("He 23 re 4444");
//				truckRequest.setDriverId("driver:abcd");
//				truckRequest.setImei("alpha");
//				truckRequest.setPassingWeight(50);
//				
//			truckData.setTruckId(truckId);
//			truckData.setTransporterId(truckRequest.getTransporterId());
//			truckData.setTruckNo(truckRequest.getTruckNo());
//			truckData.setDriverId(truckRequest.getDriverId());
//			truckData.setImei(truckRequest.getImei());
//			truckData.setPassingWeight(truckRequest.getPassingWeight());
//				
//			when(truckDao.findByTruckId(truckId)).thenReturn(truckData);		
//		//TruckData truckData = new TruckData();
//		
//			truckUpdateRequest.setImei("alphabeta");
//			
//			//truckData.setImei(truckUpdateRequest.getImei());
//			
//		//truckUpdateRequest.setTruckApproved(true);
//			//when(truckDao.findByTruckId(truckId)).thenReturn(truckData);
//			
//			response.setStatus(TruckConstants.updateSuccess);
//			response.setTransporterId(truckData.getTransporterId());
//			response.setTruckId(truckId);
//			
//		//when(truckDao.save(truckData)).thenReturn(truckData);	
//		
//
//		assertEquals(response,truckService.updateData(truckId, truckUpdateRequest));
//	}
//	
//	
//	@Test
//	public void updateTruckCase3Test(){
//		
//		//Updating Only DriverId
//		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest();
//		TruckUpdateResponse response = new TruckUpdateResponse();
//		TruckRequest truckRequest = new TruckRequest();
//		
//		TruckData truckData = new TruckData();
//			
//		String truckId = "truckId:62cc8557-52cd-4742-a11e-276cc7bec12e";	
//				
//				truckRequest.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
//				truckRequest.setTruckNo("He 23 re 4444");
//				truckRequest.setDriverId("driver:abcd");
//				truckRequest.setImei("alpha");
//				truckRequest.setPassingWeight(50);
//				
//			truckData.setTruckId(truckId);
//			truckData.setTransporterId(truckRequest.getTransporterId());
//			truckData.setTruckNo(truckRequest.getTruckNo());
//			truckData.setDriverId(truckRequest.getDriverId());
//			truckData.setImei(truckRequest.getImei());
//			truckData.setPassingWeight(truckRequest.getPassingWeight());
//				
//			when(truckDao.findByTruckId(truckId)).thenReturn(truckData);		
//		//TruckData truckData = new TruckData();
//		
//			truckUpdateRequest.setDriverId("driver:100");
//			
//			//truckData.setDriverId(truckUpdateRequest.getDriverId());
//			
//		//truckUpdateRequest.setTruckApproved(true);
//			//when(truckDao.findByTruckId(truckId)).thenReturn(truckData);
//			
//			response.setStatus(TruckConstants.updateSuccess);
//			response.setTransporterId(truckData.getTransporterId());
//			response.setTruckId(truckId);
//			
//		//when(truckDao.save(truckData)).thenReturn(truckData);	
//		
//
//		assertEquals(response,truckService.updateData(truckId, truckUpdateRequest));
//	}
//	
//	
//	
//	@Test
//	public void updateTruckCase4Test(){
//		
//		//Updating Only TruckNo having Null Value
//		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest();
//		TruckUpdateResponse response = new TruckUpdateResponse();
//		TruckRequest truckRequest = new TruckRequest();
//		
//		TruckData truckData = new TruckData();
//			
//		String truckId = "truckId:62cc8557-52cd-4742-a11e-276cc7bec12e";	
//				
//				truckRequest.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
//				truckRequest.setTruckNo("He 23 re 4444");
//				truckRequest.setDriverId("driver:abcd");
//				truckRequest.setImei("alpha");
//				truckRequest.setPassingWeight(50);
//				truckRequest.setTruckApproved(true);
//				//truckRequest.setTyres(Tyres.EIGHT_TYRES);
//				
//			truckData.setTyres(Tyres.EIGHT_TYRES);	
//			truckData.setTruckId(truckId);
//			truckData.setTransporterId(truckRequest.getTransporterId());
//			truckData.setTruckNo(truckRequest.getTruckNo());
//			truckData.setDriverId(truckRequest.getDriverId());
//			truckData.setImei(truckRequest.getImei());
//			truckData.setPassingWeight(truckRequest.getPassingWeight());
//			
//				
//			when(truckDao.findByTruckId(truckId)).thenReturn(truckData);		
//		
//		
//			truckUpdateRequest.setTruckApproved(false);
//		
//			response.setStatus(TruckConstants.updateSuccess);
//			response.setTransporterId(truckData.getTransporterId());
//			response.setTruckId(truckId);
//			
//		//when(truckDao.save(truckData)).thenReturn(truckData);	
//		
//
//		assertEquals(response,truckService.updateData(truckId, truckUpdateRequest));	
//		
//	}
//	
//	
//	@Test
//	public void updateTruckCase5Test(){
//		
//		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest();
//		TruckUpdateResponse response = new TruckUpdateResponse();
//		TruckRequest truckRequest = new TruckRequest();
//		
//		TruckData truckData = new TruckData();
//			
//		String truckId = "truckId:62cc8557-52cd-4742-a11e-276cc7bec12e";	
//				
//				truckRequest.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
//				truckRequest.setTruckNo("He 23 re 4444");
//				truckRequest.setDriverId("driver:abcd");
//				truckRequest.setImei("alpha");
//				truckRequest.setPassingWeight(50);
//				
//			truckData.setTruckId(truckId);
//			truckData.setTransporterId(truckRequest.getTransporterId());
//			truckData.setTruckNo(truckRequest.getTruckNo());
//			truckData.setDriverId(truckRequest.getDriverId());
//			truckData.setImei(truckRequest.getImei());
//			truckData.setPassingWeight(truckRequest.getPassingWeight());
//				
//			when(truckDao.findByTruckId(truckId)).thenReturn(truckData);		
//		//TruckData truckData = new TruckData();
//		
//			truckUpdateRequest.setDriverId("driver:100");
//			truckUpdateRequest.setPassingWeight(100);
//			truckUpdateRequest.setImei("beta");
//			
//			
//			response.setStatus(TruckConstants.updateSuccess);
//			response.setTransporterId(truckData.getTransporterId());
//			response.setTruckId(truckId);
//			
//		//when(truckDao.save(truckData)).thenReturn(truckData);	
//		
//
//		assertEquals(response,truckService.updateData(truckId, truckUpdateRequest));
//		
//	}
	

	
	@Test
	public void updateDataFailed_AccountNotFound(){
		
		// Truck Data doesn't Exists with given TruckId
		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest();
		TruckUpdateResponse response = new TruckUpdateResponse();
		TruckRequest truckRequest = new TruckRequest();
		
		TruckData truckData = new TruckData();
			
		String truckId = "truckId:62cc8557-52cd-4742-a11e-276cc7bec12e";	

		String truckId1 = "truckId:62cc8557-52cd-4742-a11e-276cc7abcde";	
	
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
			
			
			response.setStatus(TruckConstants.AccountNotFoundError);
			
	//	when(truckDao.save(truckData)).thenReturn(truckData);	
		

		assertEquals(response,truckService.updateData(truckId1, truckUpdateRequest));

	}
	

	@Test
	public void getTruckDataPagableSuccess_TransporterId(){
		
		//Getting All TruckData having Given TransporterId default PageNo=0 
		Integer pageNo;
		String transporterId;
		Boolean truckApproved;
		Pageable p;
		
		List<TruckData> listTruckData = new ArrayList<>();
		
		TruckData t1 = new TruckData("id1","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","AP 32 AD 2222",false,null, 0, null, null, null);
		TruckData t2 = new TruckData("id2","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2221",false,null, 0, null, null, null);
		TruckData t3 = new TruckData("id3","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2220",false,null, 0, null, null, null);
		TruckData t4 = new TruckData("id4","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2219",true,null, 0, null,null, null);
		TruckData t5 = new TruckData("id5","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68","Ap32ad2219",true,null,  0, null, null, null);
		TruckData t6 = new TruckData("id6","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68","Ap32ad2211",false,null,  0, null, null, null);
		
		listTruckData.add(t1);
		listTruckData.add(t2);
		listTruckData.add(t3);
		listTruckData.add(t4);
		listTruckData.add(t5);
		listTruckData.add(t6);
		
		
		//Case:1
		
		transporterId = "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67";
		truckApproved = null;
		String truckId=null;
		
		//List<String> arrlist2 = arrlist.subList(2, 4);
		  
		pageNo=0;
		p = PageRequest.of(0,2);
		
		when(truckDao.findByTransporterId(transporterId,p)).thenReturn(listTruckData.subList(0, 2));
		assertEquals(listTruckData.subList(0, 2),truckService.getTruckDataPagableService(pageNo,transporterId, truckApproved, truckId));
		
		pageNo=1;
		p = PageRequest.of(1,2);
		when(truckDao.findByTransporterId(transporterId,p)).thenReturn(listTruckData.subList(2, 4));
		assertEquals(listTruckData.subList(2, 4),truckService.getTruckDataPagableService(pageNo,transporterId, truckApproved, truckId));
		
		pageNo=2;
		p = PageRequest.of(2,2);
		when(truckDao.findByTransporterId(transporterId,p)).thenReturn(null);
		assertEquals(null,truckService.getTruckDataPagableService(pageNo,transporterId, truckApproved, truckId));
		
		
	}
	
	
	
	@Test
	public void getTruckDataPagableSuccess_TruckApproved(){
		
		//Getting All TruckData Which have Approved is True 
		Integer pageNo;
		String transporterId;
		Boolean truckApproved;
		Pageable p;
		
		List<TruckData> listTruckData = new ArrayList<>();
		
		TruckData t1 = new TruckData("id1","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","AP 32 AD 2222",false,null, 0, null, null, null);
		TruckData t2 = new TruckData("id2","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2221",false,null, 0, null, null, null);
		TruckData t3 = new TruckData("id3","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2220",false,null, 0, null, null, null);
		TruckData t4 = new TruckData("id4","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2219",true,null, 0, null,null, null);
		TruckData t5 = new TruckData("id5","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68","Ap32ad2219",true,null,  0, null, null, null);
		TruckData t6 = new TruckData("id6","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68","Ap32ad2211",false,null,  0, null, null, null);
		
		listTruckData.add(t1);
		listTruckData.add(t2);
		listTruckData.add(t3);
		listTruckData.add(t4);
		listTruckData.add(t5);
		listTruckData.add(t6);
		
		
		//Case:1
		
		transporterId = null;
		truckApproved = true;
		String truckId=null;
		  
		pageNo=0;
		p = PageRequest.of(0,2);
		
		when(truckDao.findByTruckApproved(truckApproved,p)).thenReturn(listTruckData.subList(3, 5));
		assertEquals(listTruckData.subList(3, 5),truckService.getTruckDataPagableService(pageNo,transporterId, truckApproved, truckId));
		
		pageNo=1;
		p = PageRequest.of(1,2);
		when(truckDao.findByTruckApproved(truckApproved,p)).thenReturn(null);
		assertEquals(null,truckService.getTruckDataPagableService(pageNo,transporterId, truckApproved, truckId));
		
		pageNo=2;
		p = PageRequest.of(2,2);
		when(truckDao.findByTruckApproved(truckApproved,p)).thenReturn(null);
		assertEquals(null,truckService.getTruckDataPagableService(pageNo,transporterId, truckApproved, truckId));
		}

	@Test
	public void getTruckDataPagableSuccess_TransporterIdAndTruckApproved(){
		
		//Getting All TruckData with Approved true and With Given TransporterID;
		Integer pageNo;
		String transporterId;
		Boolean truckApproved;
		Pageable p;
		
		List<TruckData> listTruckData = new ArrayList<>();
		
		TruckData t1 = new TruckData("id1","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","AP 32 AD 2222",false,null, 0, null, null, null);
		TruckData t2 = new TruckData("id2","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2221",false,null, 0, null, null, null);
		TruckData t3 = new TruckData("id3","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2220",false,null, 0, null, null, null);
		TruckData t4 = new TruckData("id4","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","Ap32ad2219",true,null, 0, null,null, null);
		TruckData t5 = new TruckData("id5","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68","Ap32ad2219",true,null,  0, null, null, null);
		TruckData t6 = new TruckData("id6","transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68","Ap32ad2211",false,null,  0, null, null, null);
		
		listTruckData.add(t1);
		listTruckData.add(t2);
		listTruckData.add(t3);
		listTruckData.add(t4);
		listTruckData.add(t5);
		listTruckData.add(t6);
		
		
		transporterId = "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68";
		truckApproved = true;
		String truckId=null;
		  
		pageNo=0;
		p = PageRequest.of(0,2);
		
		when(truckDao.findByTransporterIdAndTruckApproved(transporterId,truckApproved,p)).thenReturn(listTruckData.subList(4, 5));
		assertEquals(listTruckData.subList(4, 5),truckService.getTruckDataPagableService(pageNo,transporterId, truckApproved, truckId));
		
		pageNo=1;
		p = PageRequest.of(1,2);
		when(truckDao.findByTransporterIdAndTruckApproved(transporterId,truckApproved,p)).thenReturn(null);
		assertEquals(null,truckService.getTruckDataPagableService(pageNo,transporterId, truckApproved, truckId));
		
		pageNo=2;
		p = PageRequest.of(2,2);
		when(truckDao.findByTransporterIdAndTruckApproved(transporterId,truckApproved,p)).thenReturn(null);
		assertEquals(null,truckService.getTruckDataPagableService(pageNo,transporterId, truckApproved, truckId));
		}
	
	
	@Test
	public void deleteDataSuccess(){
	
		TruckDeleteResponse response = new TruckDeleteResponse();
		TruckRequest truckRequest = new TruckRequest();
		
		TruckData truckData = new TruckData();
			
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
			
			
			TruckTransporterData truckTransporterData = new TruckTransporterData();
			truckTransporterData.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
	        truckTransporterData.setTruckId(truckId);
	        
	        when(sTruckDao.findByTruckId(truckId)).thenReturn(truckTransporterData);

				
			when(truckDao.findByTruckId(truckId)).thenReturn(truckData);		
		
			
			response.setStatus(TruckConstants.deleteSuccess);
			
		assertEquals(response,truckService.deleteData(truckId));

     
	}
	
	
	
	@Test
	public void deleteDataFailed_AccountNotFound(){
		
		
		TruckRequest truckRequest = new TruckRequest();
		
		TruckData truckData = new TruckData();
		
				
			TruckDeleteResponse response = new TruckDeleteResponse();
				
			
			String truckId = "truckId:62cc8557-52cd-4742-a11e-276cc7bec12e";	

			String truckId1 = "truckId:62cc8557-52cd-4742-a11e-276cc7abcde";	
				
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
				
				
				TruckTransporterData truckTransporterData = new TruckTransporterData();
				truckTransporterData.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		        truckTransporterData.setTruckId(truckId);
		        
		        when(sTruckDao.findByTruckId(truckId)).thenReturn(truckTransporterData);

					
				when(truckDao.findByTruckId(truckId)).thenReturn(truckData);		
			
				response.setStatus(TruckConstants.AccountNotFoundError);
		
		assertEquals(response,truckService.deleteData(truckId1));
	
	}
	

}
