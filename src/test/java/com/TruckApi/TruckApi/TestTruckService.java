
package com.TruckApi.TruckApi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Arrays;

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
import com.TruckApi.TruckApi.Model.TruckUpdateResponse;
import com.TruckApi.TruckApi.Service.TruckService;
import com.TruckApi.TruckApi.Service.TruckServiceImpl;
import com.TruckApi.TruckApi.entities.TruckData;
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

	// private TruckConstants truckConstants = new TruckConstants();

	@Test
	public void addDataSuccess() {

		// When Everythimg Is Fine

		TruckRequest truckRequest = new TruckRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
				"AP 32 AD 2220", "alpha", (long) 50, "driver:0de885e0-5f43-4c68-8dde-b25464747865", 16, (long) 60,
				TruckRequest.TruckType.OPEN_HALF_BODY);



		List<TruckData> listTruckData = createTruckData();

		when(truckDao.save(listTruckData.get(0))).thenReturn(listTruckData.get(0));

		TruckCreateResponse truckCreateResponse = new TruckCreateResponse(TruckConstants.ADD_SUCCESS,
				"transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", null, "AP 32 AD 2220",false, "alpha", (long) 50,
				"driver:0de885e0-5f43-4c68-8dde-b25464747865", 16, (long) 60, TruckRequest.TruckType.OPEN_HALF_BODY);
		
		assertEquals(truckCreateResponse.getStatus(), truckService.addData(truckRequest).getStatus());

	}

	@Test
	public void addDataFailed_invalidTransporterId() {

		// When Transporter Id Is Null
		TruckRequest truckRequest = new TruckRequest(null, "AP 32 AD 2220", null, (long) 0, null, null, null, null);

		List<TruckData> listTruckData = createTruckData();

		when(truckDao.save(listTruckData.get(1))).thenReturn(listTruckData.get(1));

		TruckCreateResponse response = new TruckCreateResponse(TruckConstants.IN_CORRECT_TRANSPORTER_ID, null, null, null, null, null, 0, null, null, null, null);

		assertEquals(response, truckService.addData(truckRequest));

	}

	@Test
	public void addDataFailed_invalidTruckNo_null() {

		// When TruckNo Is NULL
		TruckRequest truckRequest = new TruckRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", null, null, 0, null, null, null, null);

		List<TruckData> listTruckData = createTruckData();

		when(truckDao.save(listTruckData.get(2))).thenReturn(listTruckData.get(2));

		TruckCreateResponse response = new TruckCreateResponse(TruckConstants.TRUCK_NO_IS_INVALID, null, null, null, null, null, 0, null, null, null, null);

		assertEquals(response, truckService.addData(truckRequest));

	}

	@Test
	public void addDataFailed_invalidTruckNo_notNull1() {

		// When TruckNo Is NULL
		TruckRequest truckRequest = new TruckRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68", "A32ad2219",
				null, 0, null, null, null, null);

		List<TruckData> listTruckData = createTruckData();

		when(truckDao.save(listTruckData.get(5))).thenReturn(listTruckData.get(5));

		TruckCreateResponse response = new TruckCreateResponse(TruckConstants.TRUCK_NO_IS_INVALID, null, null, null, null, null, 0, null, null, null, null);

		assertEquals(response, truckService.addData(truckRequest));

	}

	@Test
	public void addDataFailed_invalidTruckNo_notNull2() {

		// When TruckNo Is NULL
		TruckRequest truckRequest = new TruckRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68", "Ap32ad221",
				null, 0, null, null, null, null);

		List<TruckData> listTruckData = createTruckData();

		when(truckDao.save(listTruckData.get(6))).thenReturn(listTruckData.get(6));

		TruckCreateResponse response = new TruckCreateResponse(TruckConstants.TRUCK_NO_IS_INVALID, null, null, null, null, null, 0, null, null, null, null);

		assertEquals(response, truckService.addData(truckRequest));

	}


	@Test
	public void getTruckDataWithIdSuccess() {
		// get all Data
		List<TruckData> listTruckData = createTruckData();

		when(truckDao.findByTruckId(TruckConstants.TRUCK_ID)).thenReturn(listTruckData.get(0));
		assertEquals(listTruckData.get(0), truckService.getDataById(TruckConstants.TRUCK_ID));

	}

	@Test
	public void updateSuccess() {

		List<TruckData> listTruckData = createTruckData();

		when(truckDao.findByTruckId(TruckConstants.TRUCK_ID)).thenReturn(listTruckData.get(0));

		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest(false,"beta",(long) 1000, "driver:abccde",
				null, null, null);

		TruckUpdateResponse response = new TruckUpdateResponse(TruckConstants.UPDATE_SUCCESS,
				listTruckData.get(0).getTransporterId(), TruckConstants.TRUCK_ID, "AP 32 AD 2220",false,"beta" , (long) 1000, "driver:abccde",
				null, null, null);
		
		assertEquals(response, truckService.updateData(TruckConstants.TRUCK_ID, truckUpdateRequest));

	}

	@Test
	public void updateDataFailed_AccountNotFound() {

		// Truck Data doesn't Exists with given TruckId

		String wrongTruckId = "truckId:62cc8557-52cd-4742-a11e-276cc7abcde";

		List<TruckData> listTruckData = createTruckData();

		when(truckDao.findByTruckId(TruckConstants.TRUCK_ID)).thenReturn(listTruckData.get(0));

		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest(false,"beta",(long) 1000, "driver:abccde",
				null, null, null);

//		TruckUpdateResponse response = new TruckUpdateResponse(TruckConstants.UPDATE_SUCCESS,
//				listTruckData.get(0).getTransporterId(), TruckConstants.TRUCK_ID, "AP 32 AD 2220",false,"beta" , (long) 1000, "driver:abccde",
//				null, null, null);

		TruckUpdateResponse response = new TruckUpdateResponse(TruckConstants.ACCOUNT_NOT_FOUND_ERROR, null, null,null, null, wrongTruckId, 0, null, null, null, null);

		assertEquals(response, truckService.updateData(wrongTruckId, truckUpdateRequest));

	}

	@Test
	public void getTruckDataPagableSuccess_TransporterId() {

		// Getting All TruckData having Given TransporterId default PageNo=0

		String transporterId = TruckConstants.TRANSPORTER_ID;
		Boolean truckApproved = null;
		String truckId = null;

		Pageable currentPage;
		Integer pageNo;

		List<TruckData> listTruckData = createTruckData();

		pageNo = 0;
		currentPage = PageRequest.of(0, 15);

		when(truckDao.findByTransporterId(transporterId, currentPage)).thenReturn(listTruckData.subList(3, 5));
		assertEquals(listTruckData.subList(3, 5),
				truckService.getTruckDataPagableService(pageNo, transporterId, truckApproved, truckId));



	}

	@Test
	public void getTruckDataPagableSuccess_TruckApproved() {

		// Getting All TruckData Which have Approved is false
		String transporterId = null;
		Boolean truckApproved = false;
		String truckId = null;

		Pageable currentPage;
		Integer pageNo;

		List<TruckData> listTruckData = createTruckData();

		pageNo = 0;
		currentPage = PageRequest.of(0, 15);

		when(truckDao.findByTruckApproved(truckApproved, currentPage)).thenReturn(listTruckData.subList(5, 7));
		assertEquals(listTruckData.subList(5, 7),
				truckService.getTruckDataPagableService(pageNo, transporterId, truckApproved, truckId));

		}

	@Test
	public void getTruckDataPagableSuccess_TransporterIdAndTruckApproved() {

		// Getting All TruckData with Approved true and With Given TransporterID;
		String transporterId = TruckConstants.TRANSPORTER_ID;
		Boolean truckApproved = true;
		String truckId = null;

		Pageable currentPage;
		Integer pageNo;

		List<TruckData> listTruckData = createTruckData();

		pageNo = 0;
		currentPage = PageRequest.of(0, 15);

		when(truckDao.findByTransporterIdAndTruckApproved(transporterId, truckApproved, currentPage))
				.thenReturn(listTruckData.subList(3, 5));
		assertEquals(listTruckData.subList(3, 5),
				truckService.getTruckDataPagableService(pageNo, transporterId, truckApproved, truckId));

		}

	@Test
	public void deleteDataSuccess() {

		// Please check this....I don't know why this is failing.....updateDataSuccess
		// which is similar to it is passing though

		List<TruckData> listTruckData = createTruckData();

		when(truckDao.findByTruckId(TruckConstants.TRUCK_ID)).thenReturn(listTruckData.get(0));

		TruckDeleteResponse response = new TruckDeleteResponse(TruckConstants.DELETE_SUCCESS);

		assertEquals(response, truckService.deleteData(TruckConstants.TRUCK_ID));

	}

	@Test
	public void deleteDataFailed_AccountNotFound() {

		String wrongTruckId = "truckId:62cc8557-52cd-4742-a11e-276cc7abcde";

		List<TruckData> listTruckData = createTruckData();

		when(truckDao.findByTruckId(TruckConstants.TRUCK_ID)).thenReturn(listTruckData.get(0));

		TruckDeleteResponse response = new TruckDeleteResponse();

		assertEquals(response, truckService.deleteData(wrongTruckId));

	}

	public List<TruckData> createTruckData() {
		List<TruckData> truckList = Arrays.asList(
						new TruckData(TruckConstants.TRUCK_ID, "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
								"AP 32 AD 2220", true, "alpha", (long) 50, "driver:0de885e0-5f43-4c68-8dde-b25464747865",
								16,(long) 40, TruckData.TruckType.OPEN_HALF_BODY),
						new TruckData("id1", null, "AP 32 AD 2226", true, null, (long) 0, null, null, null, null),
						new TruckData("id2", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", null, true, null, (long) 0, null,
								null, null, null),
						new TruckData("id3", TruckConstants.TRANSPORTER_ID, "AP 32 AD 2220", true, null, (long) 0, null, null, null, null),
						new TruckData("id4", TruckConstants.TRANSPORTER_ID, "Ap32ad2219", true, null, (long) 0, null, null, null, null),
						new TruckData("id5", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68", "A32ad2219", false, null, (long) 0,
								null, null, (long) 30, null),
						new TruckData("id6", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68", "Ap32ad221", false, null, (long) 0,
								null, null, (long) 40, null));

		return truckList;
	}

}