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
	
<<<<<<< HEAD
	
	
=======
>>>>>>> 2b02d77b3e4725b96b71c1f2ea692a7e9f105831
	// Creating dao interface for truck - transporter table
	@Autowired
	private SecondTruckDao sTruckDao;
	
	
<<<<<<< HEAD
	public TruckCreateResponse addData(TruckRequest truckRequest) {
=======
	
	public TruckCreateResponse addData(TruckRequest truckRequest) {
		// TODO Auto-generated method stub
	
>>>>>>> 2b02d77b3e4725b96b71c1f2ea692a7e9f105831
		
		// object for postResponse
				TruckCreateResponse truckCreateResponse = new TruckCreateResponse();
		
		
<<<<<<< HEAD
		// handeling for in-correct transporterId as NULL;
		if(truckRequest.getTransporterId()==null)
=======
		//handeling for in-correct transporterId as NULL;
		if(truckRequest.getTransporter_id()==null)
>>>>>>> 2b02d77b3e4725b96b71c1f2ea692a7e9f105831
		{
			truckCreateResponse.setStatus("Failed: Enter Correct Transporter Id");
			return truckCreateResponse;
		}
		
		
		
		//handeling unprocessed TruckNo.
		String truckNo = truckRequest.getTruckNo();
<<<<<<< HEAD
		
		
		
		// removing all unnecessary sign's or spaces.
		String str="";
		for(int i=0;i<truckNo.length();i++)
		{
			if(truckNo.charAt(i)!=' '&&truckNo.charAt(i)!='-'&&truckNo.charAt(i)!='/')
			{
				str +=truckNo.charAt(i);
			}
		}
		
		
		
		// If truckNo is empty.
		if(str=="")
=======
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
>>>>>>> 2b02d77b3e4725b96b71c1f2ea692a7e9f105831
		{
			truckCreateResponse.setStatus("Failed: truckNo Cannot be Empty");
			return truckCreateResponse;
		}
<<<<<<< HEAD
		
		
		
		// dividing string based on contenueous sequence of integers or charectors.
		// to increase readability
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
		
		
=======
>>>>>>> 2b02d77b3e4725b96b71c1f2ea692a7e9f105831
		truckRequest.setTruckNo(truckNoUpdated);
		
		
		
<<<<<<< HEAD
		// handeling already existed same TruckId and TransporterId case
		List<TruckData> check =  truckDao.findByTransporterId(truckRequest.getTransporterId());
=======
		
		// handeling already existed same TruckId and TransporterId case
		List<TruckData> check =  truckDao.findByTransporterId(truckRequest.getTransporter_id());
>>>>>>> 2b02d77b3e4725b96b71c1f2ea692a7e9f105831

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
<<<<<<< HEAD
		data.setTransporterId(truckRequest.getTransporterId());
=======
		data.setTransporterId(truckRequest.getTransporter_id());
>>>>>>> 2b02d77b3e4725b96b71c1f2ea692a7e9f105831
		data.setTruckNo(truckRequest.getTruckNo());
		data.setApproved(false);
		data.setImei(null);
		
		truckDao.save(data);
	
		
		// sending data to truckId - TransporterId table.
		TruckTransporterData sData = new TruckTransporterData();
<<<<<<< HEAD
		sData.setTransporterId(truckRequest.getTransporterId());
		sData.setTruckId(truckId_temp);
		
=======
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
		
>>>>>>> 2b02d77b3e4725b96b71c1f2ea692a7e9f105831
		sTruckDao.save(sData);

		
		
		//Sending success postResponse
		truckCreateResponse.setStatus("Success");
<<<<<<< HEAD
		truckCreateResponse.setId(truckRequest.getTransporterId());
=======
		truckCreateResponse.setId(truckRequest.getTransporter_id());
>>>>>>> 2b02d77b3e4725b96b71c1f2ea692a7e9f105831
	
		return truckCreateResponse;
	}
	
	

<<<<<<< HEAD
=======
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

>>>>>>> 2b02d77b3e4725b96b71c1f2ea692a7e9f105831
	
	// update the approved and imei status
	public TruckUpdateResponse updateData(String id,TruckUpdateRequest truckUpdateRequest) {
		// TODO Auto-generated method stub
<<<<<<< HEAD
		
=======
>>>>>>> 2b02d77b3e4725b96b71c1f2ea692a7e9f105831
		TruckUpdateResponse response = new TruckUpdateResponse();
		TruckData temp = truckDao.findByTruckId(id);
		if(temp==null)
		{
			response.setStatus("False");
			return response;
		}
		
<<<<<<< HEAD
		
		
		if(truckUpdateRequest.getImei()!=null)
		{
			temp.setImei(truckUpdateRequest.getImei());
			temp.setApproved(false);
		}
		
		
		if(truckUpdateRequest.getTruckNo()!=null)
		{
            
			String truckNo = truckUpdateRequest.getTruckNo();
			
			// removing all unnecessary sign's or spaces.
			String str="";
			for(int i=0;i<truckNo.length();i++)
			{
				if(truckNo.charAt(i)!=' '&&truckNo.charAt(i)!='-'&&truckNo.charAt(i)!='/')
				{
					str +=truckNo.charAt(i);
				}
			}
			
			// If truckNo is empty.
			if(str=="")
			{
				response.setStatus("Failed: truckNo Cannot be Empty");
				return response;
			}
			
			
			// dividing string based on contineous sequence of integers or charectors.
			// to increase readability
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
						
			
			temp.setTruckNo(truckNoUpdated);
			temp.setApproved(false);
		}
		
		
		
		if(truckUpdateRequest.getApproved()!=null)
			temp.setApproved(truckUpdateRequest.getApproved());
=======
		if(truckUpdateRequest.getApproved()!=null)
		temp.setApproved(truckUpdateRequest.getApproved());
		
		if(truckUpdateRequest.getImei()!=null)
		temp.setImei(truckUpdateRequest.getImei());
>>>>>>> 2b02d77b3e4725b96b71c1f2ea692a7e9f105831
		
		truckDao.save(temp);
		response.setStatus("Success");
		return response;
	}

	
<<<<<<< HEAD
	
	
	
=======
>>>>>>> 2b02d77b3e4725b96b71c1f2ea692a7e9f105831
	// delete a data
	public void deleteData(String id) {
		// TODO Auto-generated method stub
		TruckData temp = truckDao.findByTruckId(id);
<<<<<<< HEAD
		TruckTransporterData temp2 = sTruckDao.findByTruckId(id);
		
		if(!Objects.isNull(temp))
		truckDao.delete(temp);
		
		if(!Objects.isNull(temp2))
	    sTruckDao.delete(temp2);
	}

	
	
	// get truck data by the truck id
	public TruckData getDataById(String Id) {
		// TODO Auto-generated method stub
                 return truckDao.findByTruckId(Id);
                
	}

	
	
	
	//get pageable
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
=======
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
>>>>>>> 2b02d77b3e4725b96b71c1f2ea692a7e9f105831
	}
	
	
}
