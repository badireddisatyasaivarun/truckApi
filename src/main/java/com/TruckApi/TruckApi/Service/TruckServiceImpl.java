package com.TruckApi.TruckApi.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.TruckApi.TruckApi.Dao.SecondTruckDao;
import com.TruckApi.TruckApi.Dao.TruckDao;
import com.TruckApi.TruckApi.Model.TruckCreateResponse;
import com.TruckApi.TruckApi.Model.TruckRequest;
import com.TruckApi.TruckApi.Model.TruckUpdateRequest;
import com.TruckApi.TruckApi.Model.TruckUpdateResponse;
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
	
	
	
	public TruckCreateResponse addData(TruckRequest truckRequest) {
		// TODO Auto-generated method stub
	
		
		// object for postResponse
				TruckCreateResponse truckCreateResponse = new TruckCreateResponse();
		
		
		//handeling for in-correct transporterId as NULL;
		if(truckRequest.getTransporter_id()==null)
		{
			truckCreateResponse.setStatus("Failed: Enter Correct Transporter Id");
			return truckCreateResponse;
		}
		
		
		
		//handeling unprocessed TruckNo.
		String truckNo = truckRequest.getTruckNo();
		String truckNoUpdated="";
		int index=0;
		for(int i=0;i<truckNo.length();i++)
		{
			if(truckNo.charAt(i)!=' ')
			{
				if(index==2||index==5||index==8)
				{
					truckNoUpdated += " ";
					index++;
				}
				truckNoUpdated +=truckNo.charAt(i);
				index++;
			}
		}
		if(index==0)
		{
			truckCreateResponse.setStatus("Failed: truckNo Cannot be Empty");
			return truckCreateResponse;
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
					truckCreateResponse.setStatus("Failed: TruckId is already Associated with TransporterId");
					//System.out.println(1);
					return truckCreateResponse;
				}
			}
			
			
		}
		
		
		// sending data to TruckData Table.
		TruckData data = new TruckData();
		String truckId_temp = "truck:"+UUID.randomUUID().toString();
		data.setTruckId(truckId_temp);
		data.setTransporterId(truckRequest.getTransporter_id());
		data.setTruckNo(truckRequest.getTruckNo());
		data.setApproved(false);
		data.setImei(null);
		
		truckDao.save(data);
	
		
		// sending data to truckId - TransporterId table.
		TruckTransporterData sData = new TruckTransporterData();
		sData.setTransporterId(truckRequest.getTransporter_id());
		sData.setTruckId(truckId_temp);
		
		
//		List<TruckData> truckDataList = truckDao.findByTruckNo(truckRequest.getTruckNo());
//		
//		for(int i=0;i<truckDataList.size();i++)
//		{
//			if(truckDataList.get(i).getTransporterId()==truckRequest.getTransporter_id())
//			{
//				sData.setTruckId(truckDataList.get(i).getTruckId());
//				break;
//			}
//		}
//		System.out.println(sData.getTransporterId());
//		System.out.println(sData.getTruckId());
		
		sTruckDao.save(sData);

		
		
		//Sending success postResponse
		truckCreateResponse.setStatus("Success");
		truckCreateResponse.setId(truckRequest.getTransporter_id());
	
		return truckCreateResponse;
	}
	
	

	// get all truck details
	public List<TruckData> getData() {
		// TODO Auto-generated method stub
		return truckDao.findAll();
	}

	
	// get truck data by the truck id
	public TruckData getDataById(String Id) {
		// TODO Auto-generated method stub
                 return truckDao.findByTruckId(Id);
                
	}

	
	// update the approved and imei status
	public TruckUpdateResponse updateData(String id,TruckUpdateRequest truckUpdateRequest) {
		// TODO Auto-generated method stub
		TruckUpdateResponse response = new TruckUpdateResponse();
		TruckData temp = truckDao.findByTruckId(id);
		if(temp==null)
		{
			response.setStatus("False");
			return response;
		}
		
		if(truckUpdateRequest.getApproved()!=null)
		temp.setApproved(truckUpdateRequest.getApproved());
		
		if(truckUpdateRequest.getImei()!=null)
		temp.setImei(truckUpdateRequest.getImei());
		
		truckDao.save(temp);
		response.setStatus("Success");
		return response;
	}

	
	// delete a data
	public void deleteData(String id) {
		// TODO Auto-generated method stub
		TruckData temp = truckDao.findByTruckId(id);
		if(!Objects.isNull(temp))
		truckDao.delete(temp);
	}


	// get data for tranporterId and Approved status with pagenation
	public List<TruckData> getDataBytransporterIdAndApprovedPage(String transporterId,Boolean approved,Integer page) {
		// TODO Auto-generated method stub
		Pageable p = PageRequest.of(page,2);
		return truckDao.findByTransporterIdAndApproved(transporterId,approved,p);
	}

	
	// get data for tranporterId with pagenation
	public List<TruckData> getDataBytransporterId(String transporterId,Integer page) {
		// TODO Auto-generated method stub
		Pageable p = PageRequest.of(page,2);
		return truckDao.findByTransporterId(transporterId,p);
	}



	public List<TruckData> getDataByapproved(Boolean approved, Integer page) {
		// TODO Auto-generated method stub
		Pageable p = PageRequest.of(page,2);
		return truckDao.findByApproved(approved,p);
	}
	
	
}
