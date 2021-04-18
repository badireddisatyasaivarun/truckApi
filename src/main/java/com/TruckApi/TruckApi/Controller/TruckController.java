package com.TruckApi.TruckApi.Controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@GetMapping("/truck")
	public List<TruckData> getTruck()
	{
		return this.truckServiceImpl.getData();
	}
	
	@GetMapping("/truck/{truckId}")
	public TruckData getTruckWithId(@PathVariable UUID truckId)
	{
		return this.truckServiceImpl.getDataById(truckId);
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

