package com.TruckApi.TruckApi.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TruckApi.TruckApi.Dao.SecondTruckDao;
import com.TruckApi.TruckApi.Dao.TruckDao;
import com.TruckApi.TruckApi.Model.TruckPostResponse;
import com.TruckApi.TruckApi.Model.TruckPutRequest;
import com.TruckApi.TruckApi.Model.TruckRequest;
import com.TruckApi.TruckApi.entities.TruckData;
import com.TruckApi.TruckApi.entities.TruckTransporterData;

@Service
public class TruckServiceImpl {

	// Creating dao interface for truck and its datails 
	@Autowired
	private TruckDao truckDao;
	
	// Creating dao interface for truck - transporter table
	@Autowired
	private SecondTruckDao sTruckDao;
	
	public TruckPostResponse addData(TruckRequest truckRequest) {
		// TODO Auto-generated method stub
		
		TruckPostResponse truckPostResponse = new TruckPostResponse();
		TruckData data = new TruckData();
		
		data.setTransporterId(truckRequest.getTransporter_id());
		data.setTruckNo(truckRequest.getTruckNo());
		data.setApproved(false);
		data.setImei(null);
		
		truckDao.save(data);
		
		TruckTransporterData sData = new TruckTransporterData();
		sData.setTransporterId(truckRequest.getTransporter_id());
		TruckData truckData = truckDao.findByTruckNo(truckRequest.getTruckNo());
		sData.setTruckId(truckData.getTruckId());
		//System.out.println(sData.getTransporterId());
		//System.out.println(sData.getTruckId());
		sTruckDao.save(sData);

		
		
		truckPostResponse.setId(truckRequest.getTransporter_id());
		truckPostResponse.setStatus("Success");
		
		return truckPostResponse;
	}
	
	

	public List<TruckData> getData() {
		// TODO Auto-generated method stub
		return truckDao.findAll();
	}

	
	public TruckData getDataById(UUID Id) {
		// TODO Auto-generated method stub
                 return truckDao.findByTruckId(Id);
	}

	
	public String updateData(UUID id,TruckPutRequest truckPutRequest) {
		// TODO Auto-generated method stub
		TruckData temp = truckDao.findByTruckId(id);
		if(temp==null)
		{
			return "Failed";
		}
		
		temp.setApproved(true);
		temp.setImei(truckPutRequest.getImeiId());
		truckDao.save(temp);
		return "Success";
	}

	
	public void deleteData(UUID id) {
		// TODO Auto-generated method stub
		TruckData temp = truckDao.findByTruckId(id);
		truckDao.delete(temp);
	}

	
	
	
}
