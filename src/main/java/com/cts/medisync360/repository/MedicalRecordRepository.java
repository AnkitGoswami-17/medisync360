package com.cts.medisync360.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.medisync360.entity.Appointment;
import com.cts.medisync360.entity.MedicalRecord;
import com.cts.medisync360.entity.Patient;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long>
{
	 public List<MedicalRecord> findByPatient(Patient patient);
	 public MedicalRecord findByAppointment(Appointment appointment);
	public MedicalRecord findByAppointmentAppointmentId(long appId);
}
