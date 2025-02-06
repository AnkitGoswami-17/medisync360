package com.cts.medisync360.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cts.medisync360.entity.Doctor;
import com.cts.medisync360.entity.Patient;
import com.cts.medisync360.entity.User;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>{
	
	public List<Doctor> findByDocSpec(String docSpec);
	
	public Doctor findByUser(User user);
	
//	@Query("select a from Appointment a where a.doctor =:doctor")
//	public List<Appointment> AllAppointmentForDoctor(Doctor doctor);
	
//	@Query("select DISTINCT d.docSpec from Doctor d")
//	public List<String> findDistinctDoctorSpecs();
	
}
