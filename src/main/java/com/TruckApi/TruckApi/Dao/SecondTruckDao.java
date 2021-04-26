package com.TruckApi.TruckApi.Dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
import com.TruckApi.TruckApi.entities.TruckData;
=======
>>>>>>> 2b02d77b3e4725b96b71c1f2ea692a7e9f105831
import com.TruckApi.TruckApi.entities.TruckTransporterData;

@Repository
public interface SecondTruckDao extends JpaRepository<TruckTransporterData,String>{

<<<<<<< HEAD
	public TruckTransporterData findByTruckId(String id);


=======
>>>>>>> 2b02d77b3e4725b96b71c1f2ea692a7e9f105831
}
