package com.TruckApi.TruckApi.Dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TruckApi.TruckApi.entities.TruckData;
import com.TruckApi.TruckApi.entities.TruckTransporterData;

@Repository
public interface SecondTruckDao extends JpaRepository<TruckTransporterData, String> {

	public TruckTransporterData findByTruckId(String id);

}
