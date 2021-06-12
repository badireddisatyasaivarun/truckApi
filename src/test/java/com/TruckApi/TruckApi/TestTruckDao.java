package com.TruckApi.TruckApi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.TruckApi.TruckApi.Constants.TruckConstants;
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

		List<TruckData> listTruckData = createTruckData();

		TruckData savedInDb = entityManager.persist(listTruckData.get(0));
		TruckData getFromDb = truckDao.findByTruckId(TruckConstants.TRUCK_ID);

		assertThat(getFromDb).isEqualTo(savedInDb);

	}

	@Test
	public void testFindByTransporterId() {

		Pageable currentPage;
		List<TruckData> listTruckData = createTruckData();

		TruckData savedInDb = entityManager.persist(listTruckData.get(3));
		TruckData savedInDb1 = entityManager.persist(listTruckData.get(4));
		TruckData savedInDb2 = entityManager.persist(listTruckData.get(5));

		currentPage = PageRequest.of(0, 3);

		Iterable<TruckData> allTrucks = truckDao.findByTransporterId(TruckConstants.TRANSPORTER_ID, currentPage);
		List<TruckData> list = new ArrayList<>();

		for (TruckData t : allTrucks) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(2);

	}

	@Test
	public void testFindByTruckApproved() {

		Pageable currentPage;
		List<TruckData> listTruckData = createTruckData();

		TruckData savedInDb = entityManager.persist(listTruckData.get(3));
		TruckData savedInDb1 = entityManager.persist(listTruckData.get(4));
		TruckData savedInDb2 = entityManager.persist(listTruckData.get(5));

		currentPage = PageRequest.of(0, 3);

		Iterable<TruckData> allTrucks = truckDao.findByTruckApproved(true, currentPage);
		List<TruckData> list = new ArrayList<>();

		for (TruckData t : allTrucks) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(2);

	}

	@Test
	public void testFindByTransporterIdAndTruckApproved() {
		Pageable currentPage;
		List<TruckData> listTruckData = createTruckData();

		TruckData savedInDb = entityManager.persist(listTruckData.get(3));
		TruckData savedInDb1 = entityManager.persist(listTruckData.get(4));
		TruckData savedInDb2 = entityManager.persist(listTruckData.get(5));

		currentPage = PageRequest.of(0, 3);

		Iterable<TruckData> allTrucks = truckDao.findByTransporterIdAndTruckApproved(TruckConstants.TRANSPORTER_ID,
				true, currentPage);
		List<TruckData> list = new ArrayList<>();

		for (TruckData t : allTrucks) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(1);

	}

	@Test
	public void testFindByTransporterIdAndTruckNo() {

		List<TruckData> listTruckData = createTruckData();

		TruckData savedInDb = entityManager.persist(listTruckData.get(3));
		TruckData savedInDb1 = entityManager.persist(listTruckData.get(4));
		TruckData savedInDb2 = entityManager.persist(listTruckData.get(5));

		Iterable<TruckData> allTrucks = truckDao.findByTransporterIdAndTruckNo(TruckConstants.TRANSPORTER_ID,
				TruckConstants.TRUCK_NO);
		List<TruckData> list = new ArrayList<>();

		for (TruckData t : allTrucks) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(1);

	}

	@Test
	public void testUpdate() {

		List<TruckData> listTruckData = createTruckData();

		TruckData savedInDb = entityManager.persist(listTruckData.get(0));

		// entityManager.persist(truckData);
		TruckData getFromDb = truckDao.findByTruckId(TruckConstants.TRUCK_ID);

		listTruckData.get(0).setPassingWeight(100);

		entityManager.persist(listTruckData.get(0));

		
		Iterable<TruckData> allTrucks = truckDao.findAll();
				
		
		List<TruckData> list = new ArrayList<>();

		for (TruckData t : allTrucks) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(1);

	}

	@Test
	public void testDelete() {

		List<TruckData> listTruckData = createTruckData();

		TruckData savedInDb = entityManager.persist(listTruckData.get(0));
		TruckData savedInDb1 = entityManager.persist(listTruckData.get(1));

		entityManager.remove(savedInDb1);

		Iterable<TruckData> allTrucks = truckDao.findAll();
		List<TruckData> list = new ArrayList<>();

		for (TruckData t : allTrucks) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(1);

	}

	public List<TruckData> createTruckData() {
		List<TruckData> truckList = Arrays.asList(new TruckData("truckId:62cc8557-52cd-4742-a11e-276cc7bec12e",
				"transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb69", "AP 32 AD 2220", true, null, 0, null, null, null),
				new TruckData("id1", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67", "AP 32 AD 2222", false, null,
						0, null, null, null),
				new TruckData("id2", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67", "Ap32ad2221", false, null, 0,
						null, null, null),
				new TruckData("id3", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67", "AP 32 AD 2220", false, null, 0,
						null, null, null),
				new TruckData("id4", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb67", "Ap32ad2219", true, null, 0,
						null, null, null),
				new TruckData("id5", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68", "Ap32ad2219", true, null, 0,
						null, null, null),
				new TruckData("id6", "transporterId:0de885e0-5f43-4c68-8dde-b0f9ff81cb68", "Ap32ad2211", false, null, 0,
						null, null, null));

		return truckList;
	}

}
