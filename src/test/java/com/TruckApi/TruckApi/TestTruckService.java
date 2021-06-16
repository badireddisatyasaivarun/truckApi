
package com.TruckApi.TruckApi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.TruckApi.TruckApi.Constants.TruckConstants;
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

@SpringBootTest
public class TestTruckService {

	@Autowired
	private TruckServiceImpl truckService;

	@MockBean
	private TruckDao truckDao;

	@MockBean
	private SecondTruckDao sTruckDao;

	@Test
	public void addDataSuccess() {

		TruckRequest truckRequest = new TruckRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
				"AP 32 AD 2220", "alpha", (long) 50, "driver:0de885e0-5f43-4c68-8dde-b25464747865", 16, (long) 60,
				TruckData.TruckType.OPEN_HALF_BODY);

		List<TruckData> listTruckData = createTruckData();

		List<TruckTransporterData> listTruckTransporterData = createTrucTransporterkData();

		when(truckDao.save(listTruckData.get(0))).thenReturn(listTruckData.get(0));

		when(sTruckDao.save(listTruckTransporterData.get(0))).thenReturn(listTruckTransporterData.get(0));

		TruckCreateResponse truckCreateResponse = new TruckCreateResponse(TruckConstants.ADD_SUCCESS,
				"transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", null, "AP 32 AD 2220", false, "alpha", (long) 50,
				"driver:0de885e0-5f43-4c68-8dde-b25464747865", 16, (long) 60, TruckData.TruckType.OPEN_HALF_BODY);

		assertEquals(truckCreateResponse.getStatus(), truckService.addData(truckRequest).getStatus());

	}

	@Test
	public void addDataFailed_invalidTransporterId() {

		TruckRequest truckRequest = new TruckRequest(null, "AP 32 AD 2220", null, (long) 0, null, null, null, null);

		List<TruckData> listTruckData = createTruckData();

		when(truckDao.save(listTruckData.get(1))).thenReturn(listTruckData.get(1));

		TruckCreateResponse response = new TruckCreateResponse(TruckConstants.IN_CORRECT_TRANSPORTER_ID, null, null,
				null, null, null, 0, null, null, null, null);

		assertEquals(response, truckService.addData(truckRequest));

	}

	@Test
	public void addDataFailed_invalidTruckNo_null() {

		TruckRequest truckRequest = new TruckRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", null, null,
				0, null, null, null, null);

		List<TruckData> listTruckData = createTruckData();

		when(truckDao.save(listTruckData.get(2))).thenReturn(listTruckData.get(2));

		TruckCreateResponse response = new TruckCreateResponse(TruckConstants.TRUCK_NO_IS_INVALID, null, null, null,
				null, null, 0, null, null, null, null);

		assertEquals(response, truckService.addData(truckRequest));

	}

	@Test
	public void addDataFailed_invalidTruckNo_notNull1() {

		TruckRequest truckRequest = new TruckRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68", "A32ad2219",
				null, 0, null, null, null, null);

		List<TruckData> listTruckData = createTruckData();

		when(truckDao.save(listTruckData.get(5))).thenReturn(listTruckData.get(5));

		TruckCreateResponse response = new TruckCreateResponse(TruckConstants.TRUCK_NO_IS_INVALID, null, null, null,
				null, null, 0, null, null, null, null);

		assertEquals(response, truckService.addData(truckRequest));

	}

	@Test
	public void addDataFailed_invalidTruckNo_notNull2() {

		TruckRequest truckRequest = new TruckRequest("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68", "Ap32ad221",
				null, 0, null, null, null, null);

		List<TruckData> listTruckData = createTruckData();

		when(truckDao.save(listTruckData.get(6))).thenReturn(listTruckData.get(6));

		TruckCreateResponse response = new TruckCreateResponse(TruckConstants.TRUCK_NO_IS_INVALID, null, null, null,
				null, null, 0, null, null, null, null);

		assertEquals(response, truckService.addData(truckRequest));

	}

	@Test
	public void getTruckDataWithIdSuccess() {

		List<TruckData> listTruckData = createTruckData();

		when(truckDao.findByTruckId(TruckConstants.TRUCK_ID)).thenReturn(listTruckData.get(0));
		assertEquals(listTruckData.get(0), truckService.getDataById(TruckConstants.TRUCK_ID));

	}

	@Test
	public void updateSuccess() {

		List<TruckData> listTruckData = createTruckData();

		when(truckDao.findByTruckId(TruckConstants.TRUCK_ID)).thenReturn(listTruckData.get(0));

		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest(false, "beta", (long) 1000, "driver:abccde",
				null, null, null);

		TruckUpdateResponse response = new TruckUpdateResponse(TruckConstants.UPDATE_SUCCESS,
				listTruckData.get(0).getTransporterId(), TruckConstants.TRUCK_ID, "AP 32 AD 2220", false, "beta",
				(long) 1000, "driver:abccde", 16, (long) 40, TruckData.TruckType.OPEN_HALF_BODY);

		assertEquals(response, truckService.updateData(TruckConstants.TRUCK_ID, truckUpdateRequest));

	}

	@Test
	public void updateDataFailed_AccountNotFound() {

		String wrongTruckId = "truckId:62cc8557-52cd-4742-a11e-276cc7abcde";

		List<TruckData> listTruckData = createTruckData();

		when(truckDao.findByTruckId(TruckConstants.TRUCK_ID)).thenReturn(listTruckData.get(0));

		TruckUpdateRequest truckUpdateRequest = new TruckUpdateRequest(false, "beta", (long) 1000, "driver:abccde",
				null, null, null);

		TruckUpdateResponse response = new TruckUpdateResponse(TruckConstants.ACCOUNT_NOT_FOUND_ERROR, null, null, null,
				null, null, 0, null, null, null, null);

		assertEquals(response, truckService.updateData(wrongTruckId, truckUpdateRequest));

	}

	@Test
	public void getTruckDataPagableSuccess_TransporterId() {

		String transporterId = TruckConstants.TRANSPORTER_ID;
		Boolean truckApproved = null;
		String truckId = null;

		Pageable currentPage;
		Integer pageNo;

		List<TruckData> listTruckData = createTruckData();

		pageNo = 0;
		currentPage = PageRequest.of(0, (int) TruckConstants.pageSize);

		when(truckDao.findByTransporterId(transporterId, currentPage)).thenReturn(listTruckData.subList(3, 5));
		assertEquals(listTruckData.subList(3, 5),
				truckService.getTruckDataPagableService(pageNo, transporterId, truckApproved, truckId));

	}

	@Test
	public void getTruckDataPagableSuccess_TruckApproved() {

		String transporterId = null;
		Boolean truckApproved = false;
		String truckId = null;

		Pageable currentPage;
		Integer pageNo;

		List<TruckData> listTruckData = createTruckData();

		pageNo = 0;
		currentPage = PageRequest.of(0, (int) TruckConstants.pageSize);

		when(truckDao.findByTruckApproved(truckApproved, currentPage)).thenReturn(listTruckData.subList(5, 7));
		assertEquals(listTruckData.subList(5, 7),
				truckService.getTruckDataPagableService(pageNo, transporterId, truckApproved, truckId));

	}

	@Test
	public void getTruckDataPagableSuccess_TransporterIdAndTruckApproved() {

		String transporterId = TruckConstants.TRANSPORTER_ID;
		Boolean truckApproved = true;
		String truckId = null;

		Pageable currentPage;
		Integer pageNo;

		List<TruckData> listTruckData = createTruckData();

		pageNo = 0;
		currentPage = PageRequest.of(0, (int) TruckConstants.pageSize);

		when(truckDao.findByTransporterIdAndTruckApproved(transporterId, truckApproved, currentPage))
				.thenReturn(listTruckData.subList(3, 5));
		assertEquals(listTruckData.subList(3, 5),
				truckService.getTruckDataPagableService(pageNo, transporterId, truckApproved, truckId));

	}

	@Test
	public void deleteDataSuccess() {

		List<TruckData> listTruckData = createTruckData();

		List<TruckTransporterData> listTruckTransporterData = createTrucTransporterkData();

		when(truckDao.findByTruckId(TruckConstants.TRUCK_ID)).thenReturn(listTruckData.get(0));

		when(sTruckDao.findByTruckId(TruckConstants.TRUCK_ID)).thenReturn(listTruckTransporterData.get(0));

		TruckDeleteResponse response = new TruckDeleteResponse(TruckConstants.DELETE_SUCCESS);

		assertEquals(response, truckService.deleteData(TruckConstants.TRUCK_ID));

	}

	@Test
	public void deleteDataFailed_AccountNotFound() {

		String wrongTruckId = "truckId:62cc8557-52cd-4742-a11e-276cc7abcde";

		List<TruckData> listTruckData = createTruckData();

		when(truckDao.findByTruckId(TruckConstants.TRUCK_ID)).thenReturn(listTruckData.get(0));

		TruckDeleteResponse response = new TruckDeleteResponse(TruckConstants.ACCOUNT_NOT_FOUND_ERROR);

		assertEquals(response, truckService.deleteData(wrongTruckId));

	}

	public List<TruckData> createTruckData() {
		List<TruckData> truckList = Arrays.asList(
				new TruckData(TruckConstants.TRUCK_ID, "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69",
						"AP 32 AD 2220", true, "alpha", (long) 50, "driver:0de885e0-5f43-4c68-8dde-b25464747865", 16,
						(long) 40, TruckData.TruckType.OPEN_HALF_BODY),
				new TruckData("id1", null, "AP 32 AD 2226", true, null, (long) 0, null, null, null, null),
				new TruckData("id2", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", null, true, null, (long) 0,
						null, null, null, null),
				new TruckData("id3", TruckConstants.TRANSPORTER_ID, "AP 32 AD 2220", true, null, (long) 0, null, null,
						null, null),
				new TruckData("id4", TruckConstants.TRANSPORTER_ID, "Ap32ad2219", true, null, (long) 0, null, null,
						null, null),
				new TruckData("id5", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68", "A32ad2219", false, null,
						(long) 0, null, null, (long) 30, null),
				new TruckData("id6", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68", "Ap32ad221", false, null,
						(long) 0, null, null, (long) 40, null));

		return truckList;
	}

	public List<TruckTransporterData> createTrucTransporterkData() {
		List<TruckTransporterData> truckList = Arrays.asList(
				new TruckTransporterData(TruckConstants.TRUCK_ID, "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69"),
				new TruckTransporterData("id3", TruckConstants.TRANSPORTER_ID));

		return truckList;
	}

}