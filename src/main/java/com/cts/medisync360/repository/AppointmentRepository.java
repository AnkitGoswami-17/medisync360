package com.cts.medisync360.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.medisync360.entity.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	public List<Appointment> findByDoctorId(long docId);

	public List<Appointment> findByPatientId(long patientId);
	
	public List<Appointment> findBySlotId(long slotId);
}
