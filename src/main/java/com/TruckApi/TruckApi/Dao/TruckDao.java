package com.TruckApi.TruckApi.Dao;

<<<<<<< HEAD

import java.util.List;
import java.util.UUID;



import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
=======
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
>>>>>>> 8033c3454448edc3d4dbe82633cd7fcff8b066a6
import org.springframework.stereotype.Repository;

import com.TruckApi.TruckApi.entities.TruckData;

<<<<<<< HEAD

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
	public List<TruckData> findByTransporterIdAndTruckNo(String transporterId, String truckNo);
	
=======
@Repository
public interface TruckDao extends JpaRepository<TruckData, String> {

	public TruckData findByTruckId(String truckId);

	public List<TruckData> findByTransporterIdAndTruckApproved(String transporterId, Boolean truckApproved,
			Pageable pageable);

	public List<TruckData> findByTransporterIdAndTruckNo(String transporterId, String truckNo);

	@Query("select t from TruckData t where t.transporterId = :transporterId")
	List<TruckData> findByTransporterId(String transporterId, Pageable pageable);

	@Query("select t from TruckData t where t.truckApproved = :truckApproved")
	List<TruckData> findByTruckApproved(Boolean truckApproved, Pageable pageable);

	@Query("select t from TruckData t where t.truckId = :truckId")
	List<TruckData> findByTruckId(String truckId, Pageable pageable);

>>>>>>> 8033c3454448edc3d4dbe82633cd7fcff8b066a6
}
