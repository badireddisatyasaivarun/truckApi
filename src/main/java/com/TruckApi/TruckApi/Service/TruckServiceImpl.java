package com.TruckApi.TruckApi.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.TruckApi.TruckApi.Constants.TruckConstants;
import com.TruckApi.TruckApi.Dao.SecondTruckDao;
import com.TruckApi.TruckApi.Dao.TruckDao;
import com.TruckApi.TruckApi.Model.TruckCreateResponse;
import com.TruckApi.TruckApi.Model.TruckDeleteResponse;
import com.TruckApi.TruckApi.Model.TruckRequest;

import com.TruckApi.TruckApi.Model.TruckUpdateRequest;
import com.TruckApi.TruckApi.Model.TruckUpdateResponse;
import com.TruckApi.TruckApi.entities.TruckData;
import com.TruckApi.TruckApi.entities.TruckData.TruckType;

import com.TruckApi.TruckApi.entities.TruckTransporterData;

@Service
public class TruckServiceImpl implements TruckService {

	@Autowired
	TruckDao truckDao;
	@Autowired
	SecondTruckDao sTruckDao;

	private TruckConstants truckConstants;

	@Override
	public TruckCreateResponse addData(TruckRequest truckRequest) {

		TruckCreateResponse truckCreateResponse = new TruckCreateResponse();

		if (truckRequest.getTransporterId() == null) {
			truckCreateResponse.setStatus(truckConstants.IN_CORRECT_TRANSPORTER_ID);
			return truckCreateResponse;
		}

		if (truckRequest.getTruckNo() == null) {
			truckCreateResponse.setStatus(truckConstants.TRUCK_NO_IS_INVALID);
			return truckCreateResponse;
		}

		String truckNo = truckRequest.getTruckNo();

		String checkTruckNo = TruckConstants.CHECK_TRUCK_NO;

		if (!truckNo.matches(checkTruckNo)) {
			truckCreateResponse.setStatus(truckConstants.TRUCK_NO_IS_INVALID);
			return truckCreateResponse;
		}

		String truckNoUpdated = generateTruckNumber(truckNo);

		// handling already existed same TruckNo and TransporterId case
		List<TruckData> check = truckDao.findByTransporterIdAndTruckNo(truckRequest.getTransporterId(), truckNoUpdated);

		if (check.size() != 0) {
			truckCreateResponse.setStatus(truckConstants.EXISTING_TRUCK_AND_TRANSPORTER);
			return truckCreateResponse;
		}

//		sending truckData to TruckData Table.
		TruckData truckData = new TruckData();

		String truckId_temp = "truck:" + UUID.randomUUID().toString();
		truckData.setTruckId(truckId_temp);
		truckData.setTransporterId(truckRequest.getTransporterId());
		truckData.setTruckNo(truckNoUpdated);
		if (truckRequest.getImei() != null) {
			truckData.setImei(truckRequest.getImei());
		}
		if (truckRequest.getPassingWeight() != 0) {
			truckData.setPassingWeight(truckRequest.getPassingWeight());
		}
		if (truckRequest.getDriverId() != null) {
			truckData.setDriverId(truckRequest.getDriverId());
		}

		if (truckRequest.getTruckLength() != null) {
			truckData.setTruckLength(truckRequest.getTruckLength());
		}
		if (truckRequest.getTruckType() != null) {

			if ("OPEN_HALF_BODY".equals(String.valueOf(truckRequest.getTruckType())))
				truckData.setTruckType(TruckType.OPEN_HALF_BODY);
			else if ("OPEN_FULL_BODY".equals(String.valueOf(truckRequest.getTruckType())))
				truckData.setTruckType(TruckType.OPEN_FULL_BODY);
			else if ("FLATBED".equals(String.valueOf(truckRequest.getTruckType())))
				truckData.setTruckType(TruckType.FLATBED);
			else if ("HALF_BODY_TRAILER".equals(String.valueOf(truckRequest.getTruckType())))
				truckData.setTruckType(TruckType.HALF_BODY_TRAILER);
			else if ("FULL_BODY_TRAILER".equals(String.valueOf(truckRequest.getTruckType())))
				truckData.setTruckType(TruckType.FULL_BODY_TRAILER);
			else if ("STANDARD_CONTAINER".equals(String.valueOf(truckRequest.getTruckType())))
				truckData.setTruckType(TruckType.STANDARD_CONTAINER);
			else
				truckData.setTruckType(TruckType.HIGH_CUBE_CONTAINER);

		}

		if (truckRequest.getTyres() != null) {
			if (truckRequest.getTyres() >= 4 && truckRequest.getTyres() <= 26 && truckRequest.getTyres() % 2 == 0) {
				truckData.setTyres(truckRequest.getTyres());
			} else {
				truckCreateResponse.setStatus(truckConstants.INVALID_NUMBER_OF_TYRES_ERROR);
				return truckCreateResponse;
			}
		}

		truckData.setTruckApproved(false);

		truckDao.save(truckData);

//		sending truckData to truckId - TransporterId table.
		TruckTransporterData sData = new TruckTransporterData();
		sData.setTransporterId(truckRequest.getTransporterId());
		sData.setTruckId(truckId_temp);

		sTruckDao.save(sData);

//		Sending success postResponse
		truckCreateResponse.setStatus(truckConstants.ADD_SUCCESS);
		truckCreateResponse.setTransporterId(truckData.getTransporterId());
		truckCreateResponse.setTruckId(truckId_temp);
		truckCreateResponse.setDriverId(truckData.getDriverId());
		truckCreateResponse.setImei(truckData.getImei());
		truckCreateResponse.setPassingWeight(truckData.getPassingWeight());
		truckCreateResponse.setTruckApproved(false);
		truckCreateResponse.setTruckLength(truckData.getTruckLength());
		truckCreateResponse.setTruckNo(truckData.getTruckNo());
		truckCreateResponse.setTruckType(truckData.getTruckType());
		truckCreateResponse.setTyres(truckData.getTyres());

		return truckCreateResponse;

	}

	public TruckUpdateResponse updateData(String id, TruckUpdateRequest truckUpdateRequest) {

		TruckUpdateResponse response = new TruckUpdateResponse();
		TruckData truckData = new TruckData();

		Optional<TruckData> findTruck = Optional.ofNullable(truckDao.findByTruckId(id));
		if (findTruck.isPresent()) {
			truckData = findTruck.get();
		} else {
			response.setStatus(truckConstants.ACCOUNT_NOT_FOUND_ERROR);
			return response;
		}

		if (truckUpdateRequest.getImei() != null) {
			truckData.setImei(truckUpdateRequest.getImei());
		}

		if (truckUpdateRequest.getPassingWeight() != 0) {
			truckData.setPassingWeight(truckUpdateRequest.getPassingWeight());
		}

		if (truckUpdateRequest.getDriverId() != null) {
			truckData.setDriverId(truckUpdateRequest.getDriverId());
		}

		if (truckUpdateRequest.getTruckApproved() != null) {
			truckData.setTruckApproved(truckUpdateRequest.getTruckApproved());

		}

		if (truckUpdateRequest.getTruckLength() != null) {
			truckData.setTruckLength(truckUpdateRequest.getTruckLength());
		}

		if (truckUpdateRequest.getTruckType() != null) {
			if ("OPEN_HALF_BODY".equals(String.valueOf(truckUpdateRequest.getTruckType())))
				truckData.setTruckType(TruckType.OPEN_HALF_BODY);
			else if ("OPEN_FULL_BODY".equals(String.valueOf(truckUpdateRequest.getTruckType())))
				truckData.setTruckType(TruckType.OPEN_FULL_BODY);
			else if ("FLATBED".equals(String.valueOf(truckUpdateRequest.getTruckType())))
				truckData.setTruckType(TruckType.FLATBED);
			else if ("HALF_BODY_TRAILER".equals(String.valueOf(truckUpdateRequest.getTruckType())))
				truckData.setTruckType(TruckType.HALF_BODY_TRAILER);
			else if ("FULL_BODY_TRAILER".equals(String.valueOf(truckUpdateRequest.getTruckType())))
				truckData.setTruckType(TruckType.FULL_BODY_TRAILER);
			else if ("STANDARD_CONTAINER".equals(String.valueOf(truckUpdateRequest.getTruckType())))
				truckData.setTruckType(TruckType.STANDARD_CONTAINER);
			else if ("HIGH_CUBE_CONTAINER".equals(String.valueOf(truckUpdateRequest.getTruckType())))
				truckData.setTruckType(TruckType.HIGH_CUBE_CONTAINER);
			else {
				response.setStatus(truckConstants.INVALID_TRUCK_TYPE_ERROR);
				return response;
			}
		}
		if (truckUpdateRequest.getTyres() != null) {
			if (truckUpdateRequest.getTyres() >= 4 && truckUpdateRequest.getTyres() <= 26
					&& truckUpdateRequest.getTyres() % 2 == 0) {
				truckData.setTyres(truckUpdateRequest.getTyres());
			} else {
				response.setStatus(truckConstants.INVALID_NUMBER_OF_TYRES_ERROR);
				return response;
			}
		}

		truckDao.save(truckData);
		response.setStatus(truckConstants.UPDATE_SUCCESS);
		response.setTransporterId(truckData.getTransporterId());
		response.setTruckId(id);
		response.setDriverId(truckData.getDriverId());
		response.setImei(truckData.getImei());
		response.setPassingWeight(truckData.getPassingWeight());
		response.setTruckApproved(truckData.getTruckApproved());
		response.setTruckLength(truckData.getTruckLength());
		response.setTruckNo(truckData.getTruckNo());
		response.setTruckType(truckData.getTruckType());
		response.setTyres(truckData.getTyres());

		return response;
	}

//	delete a truckData
	public TruckDeleteResponse deleteData(String id) {

		TruckDeleteResponse truckDeleteResponse = new TruckDeleteResponse();

		TruckData findTruckData = truckDao.findByTruckId(id);
		TruckTransporterData findTruckTransporterData = sTruckDao.findByTruckId(id);

		if (Objects.isNull(findTruckData) || Objects.isNull(findTruckTransporterData)) {
			truckDeleteResponse.setStatus(truckConstants.ACCOUNT_NOT_FOUND_ERROR);
			return truckDeleteResponse;
		} else {
			truckDao.delete(findTruckData);
			sTruckDao.delete(findTruckTransporterData);

			truckDeleteResponse.setStatus(truckConstants.DELETE_SUCCESS);
			return truckDeleteResponse;
		}

	}

	// get truck truckData by the truck id
	@Override
	public TruckData getDataById(String Id) {
		Optional<TruckData> findTruck = Optional.ofNullable(truckDao.findByTruckId(Id));
		if (findTruck.isEmpty()) {
			return null;
		}
		return findTruck.get();

	}

//	get pageable
	public List<TruckData> getTruckDataPagableService(Integer pageNo, String transporterId, Boolean truckApproved,
			String truckId) {

		if (pageNo == null)
			pageNo = 0;

		Pageable currentPage = PageRequest.of(pageNo, (int) TruckConstants.pageSize);

		if (truckId != null) {
			return truckDao.findByTruckId(truckId, currentPage);
		}

		else {
			if (transporterId != null && truckApproved == null) {
				return truckDao.findByTransporterId(transporterId, currentPage);
			}

			else if (transporterId == null && truckApproved != null) {
				return truckDao.findByTruckApproved(truckApproved, currentPage);
			}

			else if (transporterId != null && truckApproved != null) {
				return truckDao.findByTransporterIdAndTruckApproved(transporterId, truckApproved, currentPage);
			}

		}
		return truckDao.findAll();
	}

	public String generateTruckNumber(String truckNo) {

		// removing all unnecessary sign's or spaces.
		// Example: converts "AP-31-RT-4555" to "AP31RT4555" i.e removes all extra
		// spaces and character
		String str = "";

		// dividing string based on continuous sequence of integers or character.
		// to increase readability
		String truckNoUpdated = "";

		for (int i = 0; i < truckNo.length(); i++) {

			if (truckNo.charAt(i) != ' ' && truckNo.charAt(i) != '-' && truckNo.charAt(i) != '/') {

				str += truckNo.charAt(i);
			}
		}

		// iterate till last 4th index
		for (int i = 0; i < str.length() - 4; i++) {

			truckNoUpdated += str.charAt(i);

			int l1 = Integer.valueOf(str.charAt(i));
			int l2 = Integer.valueOf(str.charAt(i + 1));

			// compares present and next character having same type or not, if different
			// type, add's extra space
			// Example: converts "AP32EEE4444" to "AP 32 EEE" i.e all integers and
			// characters separate
			if ((l1 >= 48 && l1 <= 57 && (l2 < 48 || l2 > 57)) || (l2 >= 48 && l2 <= 57 && (l1 < 48 || l1 > 57))) {

				truckNoUpdated += ' ';
			}
		}

		if (truckNoUpdated.charAt(truckNoUpdated.length() - 1) != ' ') {
			truckNoUpdated += ' ';
		}

		// add's the remaining last 4digits of truckNumber
		truckNoUpdated += str.substring(str.length() - 4, str.length());

		return truckNoUpdated;
	}

}
