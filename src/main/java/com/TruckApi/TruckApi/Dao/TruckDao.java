package com.TruckApi.TruckApi.Dao;


import java.util.List;
import java.util.UUID;



import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TruckApi.TruckApi.entities.TruckData;


@Repository
public interface TruckDao extends JpaRepository<TruckData,String>{

	public TruckData findByTruckId(String truckId);
	public void deleteByTruckId(String truckId);
	public List<TruckData> findByTruckNo(String truckNo);
	public List<TruckData> findByTransporterId(String transporterId);
	public List<TruckData> findByTransporterId(String transporterId,Pageable pageable);
	public List<TruckData> findByTransporterIdAndApproved(String transporterId,Boolean approved, Pageable pageable);
	public List<TruckData> findByTransporterIdAndApproved(String transporterId,Boolean approved);
	public List<TruckData> findByApproved(Boolean approved, Pageable pageable);
	
}
