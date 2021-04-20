package com.TruckApi.TruckApi.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TruckApi.TruckApi.Model.TruckPostResponse;
import com.TruckApi.TruckApi.Model.TruckPutRequest;
import com.TruckApi.TruckApi.Model.TruckRequest;
import com.TruckApi.TruckApi.Service.TruckServiceImpl;
import com.TruckApi.TruckApi.entities.TruckData;

@RestController
public class TruckController {

	@Autowired
	private TruckServiceImpl truckServiceImpl;
	
	@GetMapping("/truck/All")
	public List<TruckData> getTruck()
	{
		return this.truckServiceImpl.getData();
	}
	
	@GetMapping("/truck/{truckId}")
	public TruckData getTruckWithId(@PathVariable UUID truckId)
	{
		return this.truckServiceImpl.getDataById(truckId);
	}
	
	@GetMapping("/truck")
	public List<TruckData> getTruckDataPagable(@RequestParam(value="pageNo",required=false) Integer pageNo,@RequestParam(value="transporterId",required=false) UUID transporterId,@RequestParam(value="approved",required=false) Boolean approved)
	{
		
//		System.out.println(transporterId);
//		System.out.println(approved);
//      localhost:8080/truck/?transporterId=df878007-80da-11e9-93dd-00163e004571&approved=false&pageNo=2
		try {
		return this.truckServiceImpl.getDataBytransporterIdAndApprovedPage(transporterId,approved,pageNo);
		}
		catch(Exception e)
		{
			return this.truckServiceImpl.getDataBytransporterIdAndApproved(transporterId,approved);
		}
		
	}
	
	
	@PostMapping("/truck")
	public TruckPostResponse addTruck(@RequestBody TruckRequest truckRequest)
	{
			return this.truckServiceImpl.addData(truckRequest);	
	}
	
	
	
	@PutMapping("/truck/{truckId}")
	public String updateTruck(@PathVariable UUID truckId,@RequestBody TruckPutRequest truckPutRequest)
	{
		return this.truckServiceImpl.updateData(truckId,truckPutRequest);
		//@PathVariable UUID truckId /{truckId}
	}
	
	
	
	@DeleteMapping("/truck/{truckId}")
	public void delete(@PathVariable UUID truckId)
	{
		this.truckServiceImpl.deleteData(truckId);
	}
	
	
	
}

