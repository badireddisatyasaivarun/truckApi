package com.TruckApi.TruckApi.Service;

import java.util.List;
import java.util.Objects;
<<<<<<< HEAD
=======
import java.util.Optional;
>>>>>>> 8033c3454448edc3d4dbe82633cd7fcff8b066a6
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
<<<<<<< HEAD
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
	
	
=======
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

		if (truckRequest.getTyres()!=null) {
			if(truckRequest.getTyres()>=4 && truckRequest.getTyres()<=26 && truckRequest.getTyres()%2==0)
			{
				truckData.setTyres(truckRequest.getTyres());
			}
			else
			{
				truckCreateResponse.setStatus(truckConstants.UNVALID_NUMBER_OF_TYRES_ERROR);
				return truckCreateResponse;
			}
		}
		else System.out.println("no: "  + truckRequest.getTyres());
		
		truckData.setTruckApproved(false);
		

		truckDao.save(truckData);

//		sending truckData to truckId - TransporterId table.
		TruckTransporterData sData = new TruckTransporterData();
		sData.setTransporterId(truckRequest.getTransporterId());
		sData.setTruckId(truckId_temp);

		sTruckDao.save(sData);

//		Sending success postResponse
		truckCreateResponse.setStatus(truckConstants.ADD_SUCCESS);
		truckCreateResponse.setTransporterId(truckRequest.getTransporterId());
		truckCreateResponse.setTruckId(truckId_temp);
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
			else
			{
				response.setStatus(truckConstants.UNVALID_TRUCK_TYPE_ERROR);
				return response;
			}
		}
		if (truckUpdateRequest.getTyres()!=null ) {
			if(truckUpdateRequest.getTyres()>=4 && truckUpdateRequest.getTyres()<=26 && truckUpdateRequest.getTyres()%2==0)
			{
				truckData.setTyres(truckUpdateRequest.getTyres());
			}
			else 
			{
				response.setStatus(truckConstants.UNVALID_NUMBER_OF_TYRES_ERROR);
				return response;
			}
		}

		truckDao.save(truckData);
		response.setStatus(truckConstants.UPDATE_SUCCESS);
		response.setTransporterId(truckData.getTransporterId());
		response.setTruckId(id);
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

		Pageable currentPage = PageRequest.of(pageNo, 2);

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

>>>>>>> 8033c3454448edc3d4dbe82633cd7fcff8b066a6
}
