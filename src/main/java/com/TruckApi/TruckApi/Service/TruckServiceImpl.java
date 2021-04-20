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
		
		
		// object for postResponse
				TruckPostResponse truckPostResponse = new TruckPostResponse();
		
		
		//handeling for in-correct transporterId as NULL;
		if(truckRequest.getTransporter_id()==null)
		{
			truckPostResponse.setStatus("Failed: Enter Correct Transporter Id");
			return truckPostResponse;
		}
		
		
		
		//handeling unprocessed TruckNo.
		String truckNo = truckRequest.getTruckNo();
		String truckNoUpdated="";
		for(int i=0;i<truckNo.length();i++)
		{
			if(truckNo.charAt(i)!=' ')
			{
				truckNoUpdated +=truckNo.charAt(i);
			}
		}
		if(truckNoUpdated=="")
		{
			truckPostResponse.setStatus("Failed: truckNo Cannot be Empty");
			return truckPostResponse;
		}
		truckRequest.setTruckNo(truckNoUpdated);
		
		
		
		// handeling already existed same TruckId and TransporterId case
		List<TruckData> check =  truckDao.findByTransporterId(truckRequest.getTransporter_id());

		if(!(Objects.isNull(check)))
		{
              // !(Objects.isNull(check))&&check.getTruckNo().equals(truckRequest.getTruckNo())
			for(int i=0;i<check.size();i++)
			{
				if(check.get(i).getTruckNo().equals(truckRequest.getTruckNo()))
				{
					truckPostResponse.setStatus("Failed: TruckId is already Associated with TransporterId");
					//System.out.println(1);
					return truckPostResponse;
				}
			}
			
			
		}
		
		
		// sending data to TruckData Table.
		TruckData data = new TruckData();
		data.setTransporterId(truckRequest.getTransporter_id());
		data.setTruckNo(truckRequest.getTruckNo());
		data.setApproved(false);
		data.setImei(null);
		
		truckDao.save(data);
		
		
		
		// sending data to truckId - TransporterId table.
		TruckTransporterData sData = new TruckTransporterData();
		sData.setTransporterId(truckRequest.getTransporter_id());
		List<TruckData> truckDataList = truckDao.findByTruckNo(truckRequest.getTruckNo());
		
		for(int i=0;i<truckDataList.size();i++)
		{
			if(truckDataList.get(i).getTransporterId()==truckRequest.getTransporter_id())
			{
				sData.setTruckId(truckDataList.get(i).getTruckId());
				break;
			}
		}
		
		//System.out.println(sData.getTransporterId());
		//System.out.println(sData.getTruckId());
		sTruckDao.save(sData);

		
		
		//Sending success postResponse
		truckPostResponse.setStatus("Success");
		truckPostResponse.setId(truckRequest.getTransporter_id());
	
		return truckPostResponse;
	}
	
	

	// get all truck details
	public List<TruckData> getData() {
		// TODO Auto-generated method stub
		return truckDao.findAll();
	}

	
	// get truck data by the truck id
	public TruckData getDataById(UUID Id) {
		// TODO Auto-generated method stub
                 return truckDao.findByTruckId(Id);
	}

	
	// update the approved and imei status
	public String updateData(UUID id,TruckPutRequest truckPutRequest) {
		// TODO Auto-generated method stub
		TruckData temp = truckDao.findByTruckId(id);
		if(temp==null)
		{
			return "Failed";
		}
		
		temp.setApproved(truckPutRequest.getApproved());
		temp.setImei(truckPutRequest.getImei());
		truckDao.save(temp);
		return "Success";
	}

	
	// delete a data
	public void deleteData(UUID id) {
		// TODO Auto-generated method stub
		TruckData temp = truckDao.findByTruckId(id);
		truckDao.delete(temp);
	}


	// get data for tranporterId and Approved status with pagenation
	public List<TruckData> getDataBytransporterIdAndApprovedPage(UUID transporterId,Boolean approved,Integer page) {
		// TODO Auto-generated method stub
		Pageable p = PageRequest.of(page,2);
		return truckDao.findByTransporterIdAndApproved(transporterId,approved,p);
	}

	
	// get data for tranporterId and Approved status without pagenation
	public List<TruckData> getDataBytransporterIdAndApproved(UUID transporterId,Boolean approved) {
		// TODO Auto-generated method stub
		return truckDao.findByTransporterIdAndApproved(transporterId,approved);
	}
	
	
}
