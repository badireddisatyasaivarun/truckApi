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

//	Creating dao interface for truck and its datails 
	@Autowired
	private TruckDao truckDao;
	
	
	
//	Creating dao interface for truck - transporter table
	@Autowired
	private SecondTruckDao sTruckDao;
	
	
	public TruckCreateResponse addData(TruckRequest truckRequest) {
		
//	    object for postResponse
		TruckCreateResponse truckCreateResponse = new TruckCreateResponse();
			
			
//		handeling for in-correct transporterId as NULL;
		if(truckRequest.getTransporterId()==null){
			
			truckCreateResponse.setStatus("Failed: Enter Correct Transporter Id");
			return truckCreateResponse;
		}
			
			
			
//		handeling unprocessed TruckNo.
		String truckNo = truckRequest.getTruckNo();
			
			
			
//		removing all unnecessary sign's or spaces.
		String str="";
		for(int i=0;i<truckNo.length();i++){
			
		    if(truckNo.charAt(i)!=' '&&truckNo.charAt(i)!='-'&&truckNo.charAt(i)!='/'){
		    	
				str +=truckNo.charAt(i);
			}
		}
			
			
			
//		If truckNo is empty.
		if(str==""){
			
			truckCreateResponse.setStatus("Failed: truckNo Cannot be Empty");
			return truckCreateResponse;
		}
			
			
			
//		dividing string based on contenueous sequence of integers or charectors.
//		to increase readability
		String truckNoUpdated="";
		for(int i=0;i<str.length();i++){
		
			truckNoUpdated+=str.charAt(i);
				
			if(i>=str.length()-1)
				break;
				
			int l1 = Integer.valueOf(str.charAt(i));
			int l2 = Integer.valueOf(str.charAt(i+1));
			if((l1>=48&&l1<=57&&(l2<48||l2>57))||(l2>=48&&l2<=57&&(l1<48||l1>57))){
				
					truckNoUpdated+=' ';
			}
		}
			
			
		truckRequest.setTruckNo(truckNoUpdated);
			
			
			
//		handeling already existed same TruckId and TransporterId case
		List<TruckData> check =  truckDao.findByTransporterId(truckRequest.getTransporterId());

		if(!(Objects.isNull(check))){
			
//	      (Objects.isNull(check))&&check.getTruckNo().equals(truckRequest.getTruckNo())
			for(int i=0;i<check.size();i++){
				
				if(check.get(i).getTruckNo().equals(truckRequest.getTruckNo())){
					
					truckCreateResponse.setStatus("Failed: TruckId is already Associated with TransporterId");
//					System.out.println(1);
					return truckCreateResponse;
				}
			}
				
				
		}
			
			
//		sending data to TruckData Table.
		TruckData data = new TruckData();
		String truckId_temp = "truck:"+UUID.randomUUID().toString();
		data.setTruckId(truckId_temp);
		data.setTransporterId(truckRequest.getTransporterId());
		data.setTruckNo(truckRequest.getTruckNo());
		data.setApproved(false);
		data.setImei(null);
			
		truckDao.save(data);
		
			
//		sending data to truckId - TransporterId table.
		TruckTransporterData sData = new TruckTransporterData();
		sData.setTransporterId(truckRequest.getTransporterId());
		sData.setTruckId(truckId_temp);
			
		sTruckDao.save(sData);

			
			
//		Sending success postResponse
		truckCreateResponse.setStatus("Success");
		truckCreateResponse.setId(truckRequest.getTransporterId());
		
		return truckCreateResponse;
	
	}
	
	

	
//	update the approved and imei status
	public TruckUpdateResponse updateData(String id,TruckUpdateRequest truckUpdateRequest) {
//		TODO Auto-generated method stub

		TruckUpdateResponse response = new TruckUpdateResponse();
		TruckData temp = truckDao.findByTruckId(id);
		if(temp==null)
		{
			response.setStatus("False");
			return response;
		}
		
		
		
		if(truckUpdateRequest.getImei()!=null)
		{
			temp.setImei(truckUpdateRequest.getImei());
			temp.setApproved(false);
		}
		
		
		if(truckUpdateRequest.getTruckNo()!=null)
		{
            
			String truckNo = truckUpdateRequest.getTruckNo();
			
//			removing all unnecessary sign's or spaces.
			String str="";
			for(int i=0;i<truckNo.length();i++)
			{
				if(truckNo.charAt(i)!=' '&&truckNo.charAt(i)!='-'&&truckNo.charAt(i)!='/')
				{
					str +=truckNo.charAt(i);
				}
			}
			
//			If truckNo is empty.
			if(str=="")
			{
				response.setStatus("Failed: truckNo Cannot be Empty");
				return response;
			}
			
			
//			dividing string based on contineous sequence of integers or charectors.
//			to increase readability
			String truckNoUpdated="";
			for(int i=0;i<str.length();i++)
			{
				truckNoUpdated+=str.charAt(i);
				
				if(i>=str.length()-1)
					break;
				
				int l1 = Integer.valueOf(str.charAt(i));
				int l2 = Integer.valueOf(str.charAt(i+1));
				if((l1>=48&&l1<=57&&(l2<48||l2>57))||(l2>=48&&l2<=57&&(l1<48||l1>57)))
				{
					truckNoUpdated+=' ';
				}
			}
			
			
//			handeling after Updated, existing two elements with same TruckId and TransporterId case
			List<TruckData> listTruckData = truckDao.findByTruckNo(truckNoUpdated);
			for(int i=0;i<listTruckData.size();i++)
			{
				if(listTruckData.get(i).getTransporterId().equals(temp.getTransporterId()))
				{
					response.setStatus("Failed: After Updating, Two elements Exists with same TruckId and TransporterId");
					return response;
				}
			}
						
			
			temp.setTruckNo(truckNoUpdated);
			temp.setApproved(false);
		}
		
		
		
		if(truckUpdateRequest.getApproved()!=null)
			temp.setApproved(truckUpdateRequest.getApproved());
		
		truckDao.save(temp);
		response.setStatus("Success");
		return response;
	}

	
	
	
	
//	delete a data
	public void deleteData(String id) {
//		TODO Auto-generated method stub
		TruckData temp = truckDao.findByTruckId(id);
		TruckTransporterData temp2 = sTruckDao.findByTruckId(id);
		
		if(!Objects.isNull(temp))
		truckDao.delete(temp);
		
		if(!Objects.isNull(temp2))
	    sTruckDao.delete(temp2);
	}

	
	
//	get truck data by the truck id
	public TruckData getDataById(String Id) {
//		TODO Auto-generated method stub
              return truckDao.findByTruckId(Id);
                
	}

	
	
	
//	get pageable
	public List<TruckData> getTruckDataPagableService(Integer pageNo,String transporterId,Boolean approved)
	{
		if(pageNo==null)
			pageNo=0;
		if(transporterId!=null&&approved==null)
		{
			Pageable p = PageRequest.of(pageNo,2);
			return truckDao.findByTransporterId(transporterId,p);
		}
		else if(transporterId==null&&approved!=null)
		{
			Pageable p = PageRequest.of(pageNo,2);
			return truckDao.findByApproved(approved,p);
		}
		else if(transporterId!=null&&approved!=null)
		{
			Pageable p = PageRequest.of(pageNo,2);
			return truckDao.findByTransporterIdAndApproved(transporterId,approved,p);
		}
		else
		{
			return truckDao.findAll();
		}
	}
	
	
}
