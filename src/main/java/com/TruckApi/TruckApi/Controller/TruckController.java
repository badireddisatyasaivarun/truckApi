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

import com.TruckApi.TruckApi.Model.TruckCreateResponse;
import com.TruckApi.TruckApi.Model.TruckRequest;
import com.TruckApi.TruckApi.Model.TruckUpdateRequest;
import com.TruckApi.TruckApi.Model.TruckUpdateResponse;
import com.TruckApi.TruckApi.Service.TruckServiceImpl;
import com.TruckApi.TruckApi.entities.TruckData;

@RestController
public class TruckController {

	@Autowired
	private TruckServiceImpl truckServiceImpl;
	
	
	@GetMapping("/truck")
	public List<TruckData> getTruckDataPagable(@RequestParam(value="pageNo",required=false) Integer pageNo,@RequestParam(value="transporterId",required=false) String transporterId,@RequestParam(value="approved",required=false) Boolean approved)
	{
	    return truckServiceImpl.getTruckDataPagableService(pageNo,transporterId,approved);
	}
	
	
	
	@GetMapping("/truck/{truckId}")
	public TruckData getTruckWithId(@PathVariable String truckId)
	{
		return truckServiceImpl.getDataById(truckId);
	}
	
	
	@PostMapping("/truck")
	public TruckCreateResponse addTruck(@RequestBody TruckRequest truckRequest)
	{
			return truckServiceImpl.addData(truckRequest);	
	}
	
	
	
	@PutMapping("/truck/{truckId}")
	public TruckUpdateResponse updateTruck(@PathVariable String truckId,@RequestBody TruckUpdateRequest truckUpdateRequest)
	{
		return truckServiceImpl.updateData(truckId,truckUpdateRequest);
	}
	
	
	
	@DeleteMapping("/truck/{truckId}")
	public void delete(@PathVariable String truckId)
	{
		truckServiceImpl.deleteData(truckId);
	}
	
	
	
}

