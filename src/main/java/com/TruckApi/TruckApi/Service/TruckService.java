package com.TruckApi.TruckApi.Service;

import java.util.List;

import com.TruckApi.TruckApi.Model.TruckCreateResponse;
import com.TruckApi.TruckApi.Model.TruckDeleteResponse;
import com.TruckApi.TruckApi.Model.TruckRequest;
import com.TruckApi.TruckApi.Model.TruckUpdateRequest;
import com.TruckApi.TruckApi.Model.TruckUpdateResponse;
import com.TruckApi.TruckApi.entities.TruckData;

public interface TruckService {
	public TruckCreateResponse addData(TruckRequest truckRequest);

	public TruckUpdateResponse updateData(String id, TruckUpdateRequest truckUpdateRequest);

	public TruckDeleteResponse deleteData(String id);

	public TruckData getDataById(String Id);

	public List<TruckData> getTruckDataPagableService(Integer pageNo, String transporterId, Boolean truckApproved,
			String truckId);

}
