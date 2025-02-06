package com.cts.medisync360.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.medisync360.entity.Patient;
import com.cts.medisync360.entity.User;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

	public Patient findByUser(User user);

}
