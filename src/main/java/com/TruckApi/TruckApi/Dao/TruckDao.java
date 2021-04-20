package com.TruckApi.TruckApi.Dao;


import java.util.List;
import java.util.UUID;



import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TruckApi.TruckApi.entities.TruckData;


@Repository
public interface TruckDao extends JpaRepository<TruckData,UUID>{

	public TruckData findByTruckId(UUID truckId);
	public void deleteByTruckId(UUID truckId);
	public List<TruckData> findByTruckNo(String truckNo);
	public List<TruckData> findByTransporterId(UUID transporterId);
	public List<TruckData> findByTransporterIdAndApproved(UUID transporterId,Boolean approved, Pageable pageable);
	public List<TruckData> findByTransporterIdAndApproved(UUID transporterId,Boolean approved);
	
}
