package com.cts.medisync360.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.cts.medisync360.dto.DoctorDto;
import com.cts.medisync360.entity.Doctor;
import com.cts.medisync360.entity.Patient;
import com.cts.medisync360.entity.Slot;
import com.cts.medisync360.entity.User;
import com.cts.medisync360.entity.UserType;
import com.cts.medisync360.repository.DoctorRepository;
import com.cts.medisync360.repository.SlotRepository;
import com.cts.medisync360.repository.UserRepository;

@Service
public class DoctorService {
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SlotRepository slotRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Lazy
	@Autowired
	private SlotService slotService;

	public Doctor registerDoctor(DoctorDto doctorDto) {
		Doctor doctor = Doctor.builder()
				.firstName(doctorDto.getFirstName())
				.lastName(doctorDto.getLastName())
				.email(doctorDto.getEmail())
				.address(doctorDto.getAddress())
				.contactNo(doctorDto.getContactNo())
				.docSpec(doctorDto.getDocSpec())
				.consultancyFees(doctorDto.getConsultancyFees())
				.build();
		
		User user = User.builder().
				userName(doctorDto.getFirstName()).
				userPassword(doctorDto.getUserPassword()).
				userType(UserType.DOCTOR).
				build();
		
		doctor.setUser(user);
		
		userService.registerUser(user);
		
		return doctorRepository.save(doctor);
	}

	public List<Doctor> getAllDoctors() {
		return doctorRepository.findAll();
	}

	public Doctor getDoctorById(long docId) {
		return doctorRepository.findById(docId).orElse(null);
	}

	public void deleteDoctorById(long docId) {
		List<Slot> slots = slotService.getSlotsByDoctor(docId);
		slotRepository.deleteAll(slots);
		doctorRepository.deleteById(docId);
	}

	public List<Doctor> getDoctorsBySpecialization(String docSpec) {
		return doctorRepository.findByDocSpec(docSpec);
	}
	
	public List<Slot> getAvailableSlotsForDoctor(long doctorId) {
		return slotService.getAvailableSlotsByDoctor(doctorId);
	}

	public Doctor updateDoctor(long docId, DoctorDto updatedDoctor) {
		return doctorRepository.findById(docId).map(doctor -> {
			doctor.setFirstName(updatedDoctor.getFirstName()!=null ? updatedDoctor.getFirstName() : doctor.getFirstName());
			doctor.setLastName(updatedDoctor.getLastName()!=null ? updatedDoctor.getLastName() : doctor.getLastName());
			doctor.setEmail(updatedDoctor.getEmail()!=null ? updatedDoctor.getEmail() : doctor.getEmail());
			doctor.setAddress(updatedDoctor.getAddress()!=null ? updatedDoctor.getAddress() : doctor.getAddress());
			doctor.setContactNo(updatedDoctor.getContactNo()!=null ? updatedDoctor.getContactNo() : doctor.getContactNo());
			doctor.setDocSpec(updatedDoctor.getDocSpec()!=null ? updatedDoctor.getDocSpec() : doctor.getDocSpec());
			doctor.setConsultancyFees(updatedDoctor.getConsultancyFees()!=null ? updatedDoctor.getConsultancyFees() : doctor.getConsultancyFees());
			return doctorRepository.save(doctor);
		}).orElseThrow(() -> new RuntimeException("Doctor not found"));
	}

	public Long getDocotorIdByUsername(String username) {
		User user = userRepository.findByUserName(username);
		if (user != null) {
			Doctor doctor = doctorRepository.findByUser(user);
			return doctor !=null ? doctor.getId() : null;
		}
		return null;
	}
}
