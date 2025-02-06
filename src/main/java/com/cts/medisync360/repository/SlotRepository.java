package com.cts.medisync360.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cts.medisync360.entity.Slot;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {

	public List<Slot> findByDoctorId(long docId);
	
	public void deleteByDoctorId(long docId);

	public List<Slot> findByDoctorIdAndIsAvailableTrue(long docId);
	
	@Query("select s from Slot s where s.isAvailable = true")
	public List<Slot> findAvailableSlots(); 

	
//	public Slot findById(long sId);
}
