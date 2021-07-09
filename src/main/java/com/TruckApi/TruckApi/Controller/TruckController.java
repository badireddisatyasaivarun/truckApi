package com.TruckApi.TruckApi.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TruckApi.TruckApi.Exception.EntityNotFoundException;
import com.TruckApi.TruckApi.Model.TruckCreateResponse;
import com.TruckApi.TruckApi.Model.TruckDeleteResponse;
import com.TruckApi.TruckApi.Model.TruckRequest;
import com.TruckApi.TruckApi.Model.TruckUpdateRequest;
import com.TruckApi.TruckApi.Model.TruckUpdateResponse;
import com.TruckApi.TruckApi.Service.TruckService;
import com.TruckApi.TruckApi.entities.TruckData;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TruckController {

	@Autowired
	private TruckService truckService;

	@GetMapping("/truck")
	@ApiOperation(value = "Find Truck Data by transporterId or by approved status with pagination", notes = "we provide the requried keys value pairs as a query parameter and get the information of all the truck's with the given key value. if pageNo is provided it i paginated else all the data available will be returned")
	public ResponseEntity<List<TruckData>> getTruckDataPagable(
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "transporterId", required = false) String transporterId,
			@RequestParam(value = "truckApproved", required = false) Boolean truckApproved,
			@RequestParam(value = "truckId", required = false) String truckId) throws EntityNotFoundException {
		log.info("Get with Params Controller Started");
		return new ResponseEntity<>(
				truckService.getTruckDataPagableService(pageNo, transporterId, truckApproved, truckId),
				HttpStatus.FOUND);
	}

	@GetMapping("/truck/{truckId}")
	@ApiOperation(value = "Find Truck Data by using truckId", notes = "we provide the Truck Id as path variable and get the information of that truck")
	public ResponseEntity<TruckData> getTruckWithId(@PathVariable String truckId) throws EntityNotFoundException {
		log.info("Get Controller Started");
		return new ResponseEntity<>(truckService.getDataById(truckId), HttpStatus.FOUND);
	}

	@PostMapping("/truck")
	@ApiOperation(value = "Add a new Truck", notes = "We add the truck to the database by providing its transporterId and TruckNo")
	public ResponseEntity<TruckCreateResponse> addTruck(@Valid @RequestBody TruckRequest truckRequest) {
		log.info("Post Controller Started");
		return new ResponseEntity<>(truckService.addData(truckRequest), HttpStatus.CREATED);
	}

	@PutMapping("/truck/{truckId}")
	@ApiOperation(value = "Update details of a particular Truck", notes = "We Update the truck Details like IMEI, Approved Status and TruckNo by providing TruckId as a path variable")
	public ResponseEntity<TruckUpdateResponse> updateTruck(@Valid @PathVariable String truckId,
			@RequestBody TruckUpdateRequest truckUpdateRequest) throws EntityNotFoundException {
		log.info("Put Controller Started");
		return new ResponseEntity<>(truckService.updateData(truckId, truckUpdateRequest), HttpStatus.OK);
	}

	@DeleteMapping("/truck/{truckId}")
	@ApiOperation(value = "Delete a Truck", notes = "We Delete the truck Details from the database by providing TruckId as a path variable")
	public ResponseEntity<TruckDeleteResponse> delete(@PathVariable String truckId) throws EntityNotFoundException {
		log.info("Delete Controller Started");
		return new ResponseEntity<>(truckService.deleteData(truckId), HttpStatus.OK);
	}

}
