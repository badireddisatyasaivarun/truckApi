package com.TruckApi.TruckApi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.TruckApi.TruckApi.Dao.TruckDao;

import com.TruckApi.TruckApi.entities.TruckData;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TestTruckDao {
	
	@Autowired
	private TestEntityManager entityManager;
	

	@Autowired
	private TruckDao truckDao;
	
	@Test
	public void testFindByTruckId() {
	
		
		TruckData truckData = new TruckData();
		
		truckData.setTruckId("truckId:0de885e0-5f43-4c68-8dde-b0f9ff12345");
		
		truckData.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckData.setTruckNo("He 23 re 4444");
		truckData.setDriverId("driver:abcd");
		truckData.setImei("alpha");
		truckData.setPassingWeight(50);
		
		TruckData savedInDb = entityManager.persist(truckData);
		TruckData getFromDb = truckDao.findByTruckId("truckId:0de885e0-5f43-4c68-8dde-b0f9ff12345");
	
		assertThat(getFromDb).isEqualTo(savedInDb);
		
	
	}
	
	
	
	@Test
	public void testFindByTransporterId() {
	
		
		TruckData truckData = new TruckData();
		
		Pageable p;
		truckData.setTruckId("truckId:0de885e0-5f43-4c68-8dde-b0f9ff12345");
		
		truckData.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckData.setTruckNo("He 23 re 4444");
		truckData.setDriverId("driver:abcd");
		truckData.setImei("alpha");
		truckData.setPassingWeight(50);
		
		TruckData truckData1 = new TruckData();
		
		
		truckData1.setTruckId("truckId:0de885e0-5f43-4c68-8dde-b0abcd12340j3n");
		
		truckData1.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9fmbkrin35");
		truckData1.setTruckNo("Ab 11 CD 1293");
		truckData1.setDriverId("driver:100");
		truckData1.setImei("beta");
		truckData1.setPassingWeight(60);
		
		TruckData truckData2 = new TruckData();
		
		
		truckData2.setTruckId("truckId:0de885e0-5f43-4c68-8dde-b0abcd123402abc5");
		
		truckData2.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckData2.setTruckNo("Ab 11 CD 1293");
		truckData2.setDriverId("driver:100");
		truckData2.setImei("beta");
		truckData2.setPassingWeight(60);	
		
		TruckData savedInDb = entityManager.persist(truckData);
		TruckData savedInDb1 = entityManager.persist(truckData1);
		TruckData savedInDb2 = entityManager.persist(truckData2);

		p = PageRequest.of(0,3);
		
		Iterable<TruckData> allTrucks = truckDao.findByTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67",p);
		List<TruckData> list = new ArrayList<>();
		
		for (TruckData t : allTrucks) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(2);
	
	
	}
	

	@Test
	public void testFindByTruckApproved() {
	
		
		TruckData truckData = new TruckData();
		
		Pageable p;
		truckData.setTruckId("truckId:0de885e0-5f43-4c68-8dde-b0f9ff12345");
		
		truckData.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckData.setTruckNo("He 23 re 4444");
		truckData.setDriverId("driver:abcd");
		truckData.setImei("alpha");
		truckData.setPassingWeight(50);
		truckData.setTruckApproved(true);
		
		TruckData truckData1 = new TruckData();
		
		
		truckData1.setTruckId("truckId:0de885e0-5f43-4c68-8dde-b0abcd12340j3n");
		
		truckData1.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9fmbkrin35");
		truckData1.setTruckNo("Ab 11 CD 1293");
		truckData1.setDriverId("driver:100");
		truckData1.setImei("beta");
		truckData1.setPassingWeight(60);
		truckData1.setTruckApproved(true);
		
		TruckData truckData2 = new TruckData();
		
		
		truckData2.setTruckId("truckId:0de885e0-5f43-4c68-8dde-b0abcd123402abc5");
		
		truckData2.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckData2.setTruckNo("Ab 11 CD 1293");
		truckData2.setDriverId("driver:100");
		truckData2.setImei("beta");
		truckData2.setPassingWeight(60);	
		truckData2.setTruckApproved(false);
		
		TruckData savedInDb = entityManager.persist(truckData);
		TruckData savedInDb1 = entityManager.persist(truckData1);
		TruckData savedInDb2 = entityManager.persist(truckData2);

		p = PageRequest.of(0,3);
		
		Iterable<TruckData> allTrucks = truckDao.findByTruckApproved(true,p);
		List<TruckData> list = new ArrayList<>();
		
		for (TruckData t : allTrucks) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(2);
	
	
	}
	
	
	
	
	
	@Test
	public void testFindByTransporterIdAndTruckApproved() {
	
		
		TruckData truckData = new TruckData();
		
		Pageable p;
		truckData.setTruckId("truckId:0de885e0-5f43-4c68-8dde-b0f9ff12345");
		
		truckData.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckData.setTruckNo("He 23 re 4444");
		truckData.setDriverId("driver:abcd");
		truckData.setImei("alpha");
		truckData.setPassingWeight(50);
		truckData.setTruckApproved(true);
		
		TruckData truckData1 = new TruckData();
		
		
		truckData1.setTruckId("truckId:0de885e0-5f43-4c68-8dde-b0abcd12340j3n");
		
		truckData1.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9fmbkrin35");
		truckData1.setTruckNo("Ab 11 CD 1293");
		truckData1.setDriverId("driver:100");
		truckData1.setImei("beta");
		truckData1.setPassingWeight(60);
		truckData1.setTruckApproved(true);
		
		TruckData truckData2 = new TruckData();
		
		
		truckData2.setTruckId("truckId:0de885e0-5f43-4c68-8dde-b0abcd123402abc5");
		
		truckData2.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckData2.setTruckNo("Ab 11 CD 1293");
		truckData2.setDriverId("driver:100");
		truckData2.setImei("beta");
		truckData2.setPassingWeight(60);	
		truckData2.setTruckApproved(false);
		
		TruckData savedInDb = entityManager.persist(truckData);
		TruckData savedInDb1 = entityManager.persist(truckData1);
		TruckData savedInDb2 = entityManager.persist(truckData2);

		p = PageRequest.of(0,3);
		
		Iterable<TruckData> allTrucks = truckDao.findByTransporterIdAndTruckApproved("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67",true,p);
		List<TruckData> list = new ArrayList<>();
		
		for (TruckData t : allTrucks) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(1);
	
	
	}
	
	
	
	
	@Test
	public void testFindByTransporterIdAndTruckNo() {
	
		
		TruckData truckData = new TruckData();
		
		truckData.setTruckId("truckId:0de885e0-5f43-4c68-8dde-b0f9ff12345");
		
		truckData.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckData.setTruckNo("He 23 re 4444");
		truckData.setDriverId("driver:abcd");
		truckData.setImei("alpha");
		truckData.setPassingWeight(50);
		truckData.setTruckApproved(true);
		
		TruckData truckData1 = new TruckData();
		
		
		truckData1.setTruckId("truckId:0de885e0-5f43-4c68-8dde-b0abcd12340j3n");
		
		truckData1.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9fmbkrin35");
		truckData1.setTruckNo("Ab 11 CD 1293");
		truckData1.setDriverId("driver:100");
		truckData1.setImei("beta");
		truckData1.setPassingWeight(60);
		truckData1.setTruckApproved(true);
		
		TruckData truckData2 = new TruckData();
		
		
		truckData2.setTruckId("truckId:0de885e0-5f43-4c68-8dde-b0abcd123402abc5");
		
		truckData2.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckData2.setTruckNo("He 23 re 4444");
		truckData2.setDriverId("driver:100");
		truckData2.setImei("beta");
		truckData2.setPassingWeight(60);	
		truckData2.setTruckApproved(false);
		
		TruckData savedInDb = entityManager.persist(truckData);
		TruckData savedInDb1 = entityManager.persist(truckData1);
		TruckData savedInDb2 = entityManager.persist(truckData2);

		
		Iterable<TruckData> allTrucks = truckDao.findByTransporterIdAndTruckNo("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67","He 23 re 4444");
		List<TruckData> list = new ArrayList<>();
		
		for (TruckData t : allTrucks) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(2);
	
	
	}
	
	
	
	
	@Test
	public void testUpdate() {
	
		
		TruckData truckData = new TruckData();
		
		truckData.setTruckId("truckId:0de885e0-5f43-4c68-8dde-b0f9ff12345");
		
		truckData.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckData.setTruckNo("He 23 re 4444");
		truckData.setDriverId("driver:abcd");
		truckData.setImei("alpha");
		truckData.setPassingWeight(50);
		
		entityManager.persist(truckData);
		TruckData getFromDb = truckDao.findByTruckId("truckId:0de885e0-5f43-4c68-8dde-b0f9ff12345");
		
		truckData.setPassingWeight(100);
		
		entityManager.persist(truckData);
		
	
		assertThat(getFromDb.getPassingWeight()).isEqualTo(100);
		
	
	}
	
	
	
	
	@Test
	public void testDelete() {
	
		
		TruckData truckData = new TruckData();
		
		truckData.setTruckId("truckId:0de885e0-5f43-4c68-8dde-b0f9ff12345");
		
		truckData.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67");
		truckData.setTruckNo("He 23 re 4444");
		truckData.setDriverId("driver:abcd");
		truckData.setImei("alpha");
		truckData.setPassingWeight(50);
		
		TruckData truckData1 = new TruckData();
		
		
		truckData1.setTruckId("truckId:0de885e0-5f43-4c68-8dde-b0abcd12340j3n");
		
		truckData1.setTransporterId("transporterId:0de885e0-5f43-4c68-8dde-b0f9fmbkrin35");
		truckData1.setTruckNo("Ab 11 CD 1293");
		truckData1.setDriverId("driver:100");
		truckData1.setImei("beta");
		truckData1.setPassingWeight(60);
		
		
		
		TruckData savedInDb = entityManager.persist(truckData);
		TruckData savedInDb1 = entityManager.persist(truckData1);
		
		
		entityManager.remove(savedInDb1);
		
	
		
		Iterable<TruckData> allTrucks = truckDao.findAll();
		List<TruckData> list = new ArrayList<>();
		
		for (TruckData t : allTrucks) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(1);
	
	
	}
	
	
	
	
}
