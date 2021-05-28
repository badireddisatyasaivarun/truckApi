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
import com.TruckApi.TruckApi.entities.TruckData.Tyres;

import com.TruckApi.TruckApi.entities.TruckTransporterData;


@Service
public class TruckServiceImpl implements TruckService {

//	Creating dao interface for truck and its datails 
	@Autowired
	TruckDao truckDao;
	
//	Creating dao interface for truck - transporter table
	@Autowired
	 SecondTruckDao sTruckDao;
	
	private TruckConstants truckConstants;
	
	@Override
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
		String checkTruckNo="^[A-Za-z]{2}[ -/]{0,1}[0-9]{1,2}[ -/]{0,1}(?:[A-Za-z]{0,1})[ -/]{0,1}[A-Za-z]{0,2}[ -/]{0,1}[0-9]{4}$";
		 
		//(([A-Za-z]){2,3}(|-)(?:[0-9]){1,2}(|-)(?:[A-Za-z]){2}(|-)([0-9]){1,4})|(([A-Za-z]){2,3}(|-)([0-9]){1,4})
		//"^[A-Za-z]{2}[ -/]{0,1}[0-9]{1,2}[ -/]{0,1}(?:[A-Za-z]{0,1})[ -/]{0,1}[A-Za-z]{0,2}[ -/]{0,1}[0-9]{1,4}$"
		
		if(!truckNo.matches(checkTruckNo)){
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

		
		//truckRequest.setTruckNo(truckNoUpdated);
		
		
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
		
		if(truckRequest.getImei()!=null) {
			data.setImei(truckRequest.getImei());
		}
		if(truckRequest.getPassingWeight()!=0.0) {
			data.setPassingWeight(truckRequest.getPassingWeight());
		}
		if(truckRequest.getDriverId()!=null) {
			data.setDriverId(truckRequest.getDriverId());
		}
		
		
		System.out.println("hey: "+TruckType.OPEN_BODY_TRUCK);
		System.out.println("hey1: "+truckRequest.getTruckType());
		
		if(truckRequest.getTruckType()!=null) {
			
			if("LCV".equals(String.valueOf(truckRequest.getTruckType())))
				data.setTruckType(TruckType.LCV);
			else if("OPEN_BODY_TRUCK".equals(String.valueOf(truckRequest.getTruckType())))
				data.setTruckType(TruckType.OPEN_BODY_TRUCK);
			else if("CLOSED_CONTAINER".equals(String.valueOf(truckRequest.getTruckType())))
				data.setTruckType(TruckType.CLOSED_CONTAINER);
			else if("TRAILER".equals(String.valueOf(truckRequest.getTruckType())))
				data.setTruckType(TruckType.TRAILER);
			else if("TANKER".equals(String.valueOf(truckRequest.getTruckType())))
				data.setTruckType(TruckType.TANKER);
			else if("TIPPER".equals(String.valueOf(truckRequest.getTruckType())))
				data.setTruckType(TruckType.TIPPER);
			else	
				data.setTruckType(TruckType.OTHERS);
			
		}
		
		if(truckRequest.getTyres()!=null) {
			if("SIX_TYRES".equals(String.valueOf(truckRequest.getTyres())))
				data.setTyres(Tyres.SIX_TYRES);
			
			else if("EIGHT_TYRES".equals(String.valueOf(truckRequest.getTyres())))
				data.setTyres(Tyres.EIGHT_TYRES);
			else
				data.setTyres(Tyres.OTHERS);
			
			
		}
		
		
		if(truckRequest.getTruckApproved()!=null) {
			data.setTruckApproved(truckRequest.getTruckApproved());
		}
		
		truckDao.save(data);
		
			
//		sending data to truckId - TransporterId table.
		TruckTransporterData sData = new TruckTransporterData();
		sData.setTransporterId(truckRequest.getTransporterId());
		sData.setTruckId(truckId_temp);
		
		sTruckDao.save(sData);
			
//		Sending success postResponse
		truckCreateResponse.setStatus(truckConstants.success);
		truckCreateResponse.setTransporterId(truckRequest.getTransporterId());
		truckCreateResponse.setTruckId(truckId_temp);
		return truckCreateResponse;
	
		
	}
	
	

	public TruckUpdateResponse updateData(String id,TruckUpdateRequest truckUpdateRequest) {

		TruckUpdateResponse response = new TruckUpdateResponse();
		TruckData td=new TruckData();

		
		Optional<TruckData> d = truckDao.findById(id);
		if(d.isPresent()) {
			td = d.get();
		}
		else {
			 response.setStatus(truckConstants.AccountNotFoundError);
			return response;
		}
		
		
		if(truckUpdateRequest.getImei()!=null)
		{
			td.setImei(truckUpdateRequest.getImei());
			}
		
		if(truckUpdateRequest.getPassingWeight()!=0.0)
		{
			td.setPassingWeight(truckUpdateRequest.getPassingWeight());
			}
		
		if(truckUpdateRequest.getDriverId()!=null)
		{
			td.setDriverId(truckUpdateRequest.getDriverId());
			}
		
		if(truckUpdateRequest.getTruckApproved()!=null)
		{
			td.setTruckApproved(truckUpdateRequest.getTruckApproved());
			
		}
		
		if(truckUpdateRequest.getTruckType()!=null) {
			
			if("LCV".equals(String.valueOf(truckUpdateRequest.getTruckType())))
				td.setTruckType(TruckType.LCV);
			else if("OPEN_BODY_TRUCK".equals(String.valueOf(truckUpdateRequest.getTruckType())))
				td.setTruckType(TruckType.OPEN_BODY_TRUCK);
			else if("CLOSED_CONTAINER".equals(String.valueOf(truckUpdateRequest.getTruckType())))
				td.setTruckType(TruckType.CLOSED_CONTAINER);
			else if("TRAILER".equals(String.valueOf(truckUpdateRequest.getTruckType())))
				td.setTruckType(TruckType.TRAILER);
			else if("TANKER".equals(String.valueOf(truckUpdateRequest.getTruckType())))
				td.setTruckType(TruckType.TANKER);
			else if("TIPPER".equals(String.valueOf(truckUpdateRequest.getTruckType())))
				td.setTruckType(TruckType.TIPPER);
			else	
				td.setTruckType(TruckType.OTHERS);
				
		}
		
		if(truckUpdateRequest.getTyres()!=null) {
			if("SIX_TYRES".equals(String.valueOf(truckUpdateRequest.getTyres())))
				td.setTyres(Tyres.SIX_TYRES);
			else if("EIGHT_TYRES".equals(String.valueOf(truckUpdateRequest.getTyres())))
				td.setTyres(Tyres.EIGHT_TYRES);
			else	
				td.setTyres(Tyres.OTHERS);
			
			
		}
		
	
		
		truckDao.save(td);
		response.setStatus(truckConstants.updateSuccess);
		response.setTransporterId(td.getTransporterId());
		response.setTruckId(id);
		return response;
	}

	
//	delete a data
	public TruckDeleteResponse deleteData(String id) {

		TruckDeleteResponse truckDeleteResponse = new TruckDeleteResponse();
		
		TruckData temp = truckDao.findByTruckId(id);
		TruckTransporterData temp2 = sTruckDao.findByTruckId(id);
		
		
		if(Objects.isNull(temp) || Objects.isNull(temp2)) {
			truckDeleteResponse.setStatus(truckConstants.AccountNotFoundError);
			return truckDeleteResponse;	
		}
		else if(!Objects.isNull(temp2) && !Objects.isNull(temp)) {
			truckDao.delete(temp);
			sTruckDao.delete(temp2);
			truckDeleteResponse.setStatus(truckConstants.deleteSuccess);
			return truckDeleteResponse;	
		}
		
		return truckDeleteResponse;
			
		
	}

	
//	get truck data by the truck id
	@Override
	public TruckData getDataById(String Id) {
//		TODO Auto-generated method stub
		Optional<TruckData> d = Optional.ofNullable(truckDao.findByTruckId(Id));
		if(d.isEmpty()) {
			return null;
		}
		return d.get();	
 
	}

	
//	get pageable
	public List<TruckData> getTruckDataPagableService(Integer pageNo,String transporterId,Boolean truckApproved,String truckId){
		
		if(pageNo==null)
			pageNo=0;

		
		Pageable p = PageRequest.of(pageNo,2);
		
		if(truckId!=null) {
			return truckDao.findByTruckId(truckId,p);
		}
		
		else {
			if(transporterId!=null && truckApproved==null) {
				return truckDao.findByTransporterId(transporterId,p);
			}

			else if(transporterId==null && truckApproved!=null ) {
				return truckDao.findByTruckApproved(truckApproved,p);
			}
		
			else if (transporterId!=null && truckApproved!=null ) {
				return truckDao.findByTransporterIdAndTruckApproved(transporterId,truckApproved,p);
			}
		
		}
		return truckDao.findAll();
	}
	
	
}
