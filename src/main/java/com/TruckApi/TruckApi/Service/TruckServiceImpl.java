package com.TruckApi.TruckApi.Service;

import java.util.List;
import java.util.Objects;
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
	
	private TruckConstants truckConstants;
	
	public TruckCreateResponse addData(TruckRequest truckRequest) {
		
//	    object for postResponse
		TruckCreateResponse truckCreateResponse = new TruckCreateResponse();
			
			
//		handeling for in-correct transporterId as NULL;
		if(truckRequest.getTransporterId()==null){
			truckCreateResponse.setStatus(truckConstants.inCorrectTransporterId);
			return truckCreateResponse;
		}
		
		
//		handeling unprocessed TruckNo.
		String truckNo = truckRequest.getTruckNo();
		
//		Invalid TruckID
		if(!truckNo.matches("^[A-Za-z]{2}[ -/]{0,1}[0-9]{1,2}[ -/]{0,1}(?:[A-Za-z]{0,1})[ -/]{0,1}[A-Za-z]{0,2}[ -/]{0,1}[0-9]{1,4}$")){
			truckCreateResponse.setStatus(truckConstants.truckNoIsInvalid);
			return truckCreateResponse;
		}
					
//		removing all unnecessary sign's or spaces.
//		Example: converts "AP-31-RT-4555" to "AP31RT4555" i.e removes all extra spaces and charectors
		String str="";
		for(int i=0;i<truckNo.length();i++){
			
		    if(truckNo.charAt(i)!=' '&&truckNo.charAt(i)!='-'&&truckNo.charAt(i)!='/'){
		    	
				str +=truckNo.charAt(i);
			}
		}
			
				
//		dividing string based on contenueous sequence of integers or charectors.
//		to increase readability
		String truckNoUpdated="";

//		iterate till last 4th index
		for(int i=0;i<str.length()-4;i++){
		
			truckNoUpdated+=str.charAt(i);
		
			int l1 = Integer.valueOf(str.charAt(i));
			int l2 = Integer.valueOf(str.charAt(i+1));
//			compares present and next charector having same type or not, if different type, add's extra space
//			Example: converts "AP32EEE4444" to "AP 32 EEE" i.e all integers and charectors seperated
			if((l1>=48&&l1<=57&&(l2<48||l2>57))||(l2>=48&&l2<=57&&(l1<48||l1>57))){
				
					truckNoUpdated+=' ';
			}
		}
		

		if(truckNoUpdated.charAt(truckNoUpdated.length()-1)!=' ')
		{
			truckNoUpdated+=' ';
		}
//		add's the remaining last 4digits of truckNumber
		truckNoUpdated+=str.substring(str.length()-4,str.length());

		
		truckRequest.setTruckNo(truckNoUpdated);
		
		
//		handeling already existed same TruckNo and TransporterId case
		List<TruckData> check = truckDao.findByTransporterIdAndTruckNo(truckRequest.getTransporterId(),truckRequest.getTruckNo());
		
		if(check.size()!=0)
		{
			truckCreateResponse.setStatus(truckConstants.existingTruckAndTransporter);
			return truckCreateResponse;
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
		truckCreateResponse.setStatus(truckConstants.success);
		truckCreateResponse.setId(truckRequest.getTransporterId());
		
		return truckCreateResponse;
	
	}
	
	
//	update the approved and imei status
	public TruckUpdateResponse updateData(String id,TruckUpdateRequest truckUpdateRequest) {

		TruckUpdateResponse response = new TruckUpdateResponse();
		TruckData temp = truckDao.findByTruckId(id);
		if(temp==null)
		{
			response.setStatus(truckConstants.failure);
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
			
//			Invalid TruckID
			if(!truckNo.matches("^[A-Za-z]{2}[ -/]{0,1}[0-9]{1,2}[ -/]{0,1}(?:[A-Za-z]{0,1})[ -/]{0,1}[A-Za-z]{0,2}[ -/]{0,1}[0-9]{1,4}$")){
				response.setStatus(truckConstants.truckNoIsInvalid);
				return response;
			}
			
//			removing all unnecessary sign's or spaces.
//	        Example: converts "AP-31-RT-4555" to "AP31RT4555" i.e removes all extra spaces and charectors
			String str="";
			for(int i=0;i<truckNo.length();i++)
			{
				if(truckNo.charAt(i)!=' '&&truckNo.charAt(i)!='-'&&truckNo.charAt(i)!='/')
				{
					str +=truckNo.charAt(i);
				}
			}
			
			
//			dividing string based on contineous sequence of integers or charectors.
//			to increase readability
			String truckNoUpdated="";

			for(int i=0;i<str.length()-4;i++)
			{
				truckNoUpdated+=str.charAt(i);
				
				int l1 = Integer.valueOf(str.charAt(i));
				int l2 = Integer.valueOf(str.charAt(i+1));
//				compares present and next charector having same type or not, if different type, add's extra space
//				Example: converts "AP32EEE4444" to "AP 32 EEE" i.e all integers and charectors seperated
				if((l1>=48&&l1<=57&&(l2<48||l2>57))||(l2>=48&&l2<=57&&(l1<48||l1>57)))
				{
					truckNoUpdated+=' ';
				}
			}	
			

			if(truckNoUpdated.charAt(truckNoUpdated.length()-1)!=' ')
			{
				truckNoUpdated+=' ';
			}
//			add's the remaining last 4digits of truckNumber
			truckNoUpdated+=str.substring(str.length()-4,str.length());

			
//			handeling after Updated, existing two elements with same TruckId and TransporterId case
			List<TruckData> listTruckData = truckDao.findByTruckNo(truckNoUpdated);
			
			for(TruckData truckData: listTruckData)
			{
				if(truckData.getTransporterId().equals(temp.getTransporterId()))
				{
					response.setStatus(truckConstants.upExistingTruckAndTransporter);
					return response;
				}
			}
					
			
			temp.setTruckNo(truckNoUpdated);
			temp.setApproved(false);
		}
		
		
		if(truckUpdateRequest.getApproved()!=null)
			temp.setApproved(truckUpdateRequest.getApproved());
		
		truckDao.save(temp);
		response.setStatus(truckConstants.success);
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
	public List<TruckData> getTruckDataPagableService(Integer pageNo,String transporterId,Boolean approved){
		
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
		return truckDao.findAll();
	}
	
	
}
