package com.cts.medisync360.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.medisync360.dto.DoctorDto;
import com.cts.medisync360.entity.Doctor;
import com.cts.medisync360.entity.Slot;
import com.cts.medisync360.service.DoctorService;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;

	@PostMapping("/register")
	public Doctor registerDoctor(@RequestBody DoctorDto doctorDto) {
		return doctorService.registerDoctor(doctorDto);
	}

	@GetMapping
	public List<Doctor> getAllDoctors() {
		return doctorService.getAllDoctors();
	}

	@GetMapping("/{docId}")
	public Doctor getDoctorById(@PathVariable long docId) {
		return doctorService.getDoctorById(docId);
	}

	@DeleteMapping("/{docId}")
	public void deleteDoctorById(@PathVariable long docId) {
		doctorService.deleteDoctorById(docId);
	}

	@GetMapping("/specialization/{docSpec}")
	public List<Doctor> getDoctorsBySpecialization(@PathVariable String docSpec) {
		return doctorService.getDoctorsBySpecialization(docSpec);
	}

	@GetMapping("/doctor/{doctorId}/available-slots")
	public List<Slot> getAvailableSlotsForDoctor(@PathVariable long doctorId) {
		return doctorService.getAvailableSlotsForDoctor(doctorId);
	}

	@PutMapping("/{docId}")
	public Doctor updateDoctor(@PathVariable long docId, @RequestBody DoctorDto updatedDoctor) {
		return doctorService.updateDoctor(docId, updatedDoctor);
	}

}
