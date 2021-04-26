package com.TruckApi.TruckApi;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
	
	
	@Test
	public void testAddTruck() 
	{
	
		TruckRequest truckRequest = new TruckRequest();
		TruckCreateResponse truckCreateResponse = new TruckCreateResponse();
		TruckData truckData = new TruckData();
		
		//Case:1
		truckRequest.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckRequest.setTruckNo("He 23 re 4444");
		
		
		truckCreateResponse.setId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckCreateResponse.setStatus("Success");
		
	
		truckData.setApproved(false);
		truckData.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckData.setTruckId(null);
		truckData.setTruckNo("He 23 re 4444");
		
        when(truckDao.save(truckData)).thenReturn(truckData);
        assertEquals(truckCreateResponse,truckController.addTruck(truckRequest));
        
        
        //Case:2
        truckRequest.setTransporterId(null);
        
        truckCreateResponse.setId(null);
		truckCreateResponse.setStatus("Failed: Enter Correct Transporter Id");
		
		
	    assertEquals(truckCreateResponse,truckController.addTruck(truckRequest));
	      
	    
	    
	    //Case:3 
	      truckRequest.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
			truckRequest.setTruckNo("");
			
			
			truckCreateResponse.setId(null);
			truckCreateResponse.setStatus("Failed: truckNo Cannot be Empty");
			
		 assertEquals(truckCreateResponse,truckController.addTruck(truckRequest));
			
		
		 //Case:4
		 truckRequest.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
			truckRequest.setTruckNo("He 23 re 4444");
			
			
			truckData.setApproved(false);
			truckData.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
			truckData.setTruckId(null);
			truckData.setTruckNo("He 23 re 4444"); 
        
			

			truckCreateResponse.setId(null);
			truckCreateResponse.setStatus("Failed: TruckId is already Associated with TransporterId");
			
		
			
			 when(truckDao.findByTransporterId(truckRequest.getTransporterId())).thenReturn(Stream.of(truckData).collect(Collectors.toList()));
	
		     assertEquals(truckCreateResponse,truckController.addTruck(truckRequest));
		        
		     
		     
        
	}
	
	
	@Test
	public void getTruckDataWithIdTest()
	{
		
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
	public void updateTruckTest()
	{
		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest();
		TruckUpdateResponse response = new TruckUpdateResponse();
		TruckData truckData = new TruckData();
		String truckId = "truckId:62cc8557-52cd-4742-a11e-276cc7bec12e";
		
		//Case:1
		truckUpdateRequest.setApproved(true);
		
		when(truckDao.save(truckData)).thenReturn(truckData);	
		when(truckDao.findByTruckId(truckId)).thenReturn(truckData);
		
		response.setStatus("Success");

		assertEquals(response,truckController.updateTruck(truckId, truckUpdateRequest));
		
		
		
		//Case:2
		truckUpdateRequest.setImei("dc2224c0-b24d-4604-91dd-a630d22dfe49");
		
		when(truckDao.save(truckData)).thenReturn(truckData);	
		when(truckDao.findByTruckId(truckId)).thenReturn(truckData);
		
		response.setStatus("Success");

		assertEquals(response,truckController.updateTruck(truckId, truckUpdateRequest));
		
		
		
		//Case3:
		truckUpdateRequest.setTruckNo("Ap32re4333");
		
		when(truckDao.save(truckData)).thenReturn(truckData);	
		when(truckDao.findByTruckId(truckId)).thenReturn(truckData);
		
		response.setStatus("Success");

		assertEquals(response,truckController.updateTruck(truckId, truckUpdateRequest));
		
		
		
		//Case:4
		truckUpdateRequest.setTruckNo("");
		when(truckDao.save(truckData)).thenReturn(truckData);	
		when(truckDao.findByTruckId(truckId)).thenReturn(truckData);
		
		response.setStatus("Failed: truckNo Cannot be Empty");

		assertEquals(response,truckController.updateTruck(truckId, truckUpdateRequest));
		
		
	}
	
	@Test
	public void getTruckDataPagableTest()
	{
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
		
		//Case:2
		pageNo=0;
		approved=true;
		transporterId=null;
		p = PageRequest.of(0,2);
		when(truckDao.findByApproved(approved,p)).thenReturn(Stream.of(t4,t5).collect(Collectors.toList()));
		assertEquals(2,truckController.getTruckDataPagable(pageNo,transporterId,approved).size());
		
		//Case:3
		pageNo=0;
		approved=true;
		transporterId="transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68";
		p = PageRequest.of(0,2);
		when(truckDao.findByTransporterIdAndApproved(transporterId,approved,p)).thenReturn(Stream.of(t5).collect(Collectors.toList()));
		assertEquals(1,truckController.getTruckDataPagable(pageNo,transporterId,approved).size());
		
		//Case:4
		pageNo=null;
		approved=null;
		transporterId=null;
		when(truckDao.findAll()).thenReturn(Stream.of(t1,t2,t3,t4,t5,t6).collect(Collectors.toList()));
		assertEquals(6,truckController.getTruckDataPagable(pageNo,transporterId,approved).size());
		
	}
	
	
	@Test
	public void deleteTest()
	{
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
