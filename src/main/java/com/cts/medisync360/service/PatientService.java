package com.cts.medisync360.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.cts.medisync360.dto.PatientDto;
import com.cts.medisync360.entity.Appointment;
import com.cts.medisync360.entity.Patient;
import com.cts.medisync360.entity.User;
import com.cts.medisync360.entity.UserType;
import com.cts.medisync360.repository.PatientRepository;
import com.cts.medisync360.repository.UserRepository;

@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;

//	@Autowired
//	private SlotService slotService;
	
	@Lazy
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	public Patient registerPatient(PatientDto patientDto) {

		Patient patient = Patient.builder().firstName(patientDto.getFirstName()).lastName(patientDto.getLastName())
				.email(patientDto.getEmail()).contactNo(patientDto.getContactNo())
				.address(patientDto.getAddress()).city(patientDto.getCity())
//				.dob(patientDto.getDob())
				.age(patientDto.getAge())
				.gender(patientDto.getGender()).build();

		User user=User.builder().
				userName(patientDto.getFirstName()).
				userPassword(patientDto.getUserPassword()).
				userType(UserType.PATIENT).build();
		
		userService.registerUser(user);
		
		patient.setUser(user);

		return patientRepository.save(patient);
	}

	public List<Patient> getAllPatient() {
		return patientRepository.findAll();
	}

	public Patient getPatientById(long patientId) {
		return patientRepository.findById(patientId).orElseThrow(() -> new RuntimeException("Patient not found"));
	}

//	public List<Appointment> getAppointmentsForPatient(long patientId) {
//		return appointmentService.getAppointmentsByPatient(patientId);
//	}

	public Patient updatePatient(long patientId, PatientDto updatedPatient) {
		return patientRepository.findById(patientId).map(patient -> {
			patient.setFirstName(updatedPatient.getFirstName() != null ? updatedPatient.getFirstName() : patient.getFirstName());
			patient.setLastName(updatedPatient.getLastName() != null ? updatedPatient.getLastName() : patient.getLastName());
			patient.setEmail(updatedPatient.getEmail() != null ? updatedPatient.getEmail() : patient.getEmail());
			patient.setContactNo(
					updatedPatient.getContactNo() != null ? updatedPatient.getContactNo() : patient.getContactNo());
			patient.setAddress(
					updatedPatient.getAddress() != null ? updatedPatient.getAddress() : patient.getAddress());
//			patient.setDob(updatedPatient.getDob() != null ? updatedPatient.getDob() : patient.getDob());
			patient.setAge(updatedPatient.getAge() != null ? updatedPatient.getAge() : patient.getAge());
			patient.setGender(updatedPatient.getGender() != null ? updatedPatient.getGender() : patient.getGender());
			patient.setCity(updatedPatient.getCity() != null ? updatedPatient.getCity() : patient.getCity());
			return patientRepository.save(patient);
		}).orElseThrow(() -> new RuntimeException("Patient not found"));
	}

	public void deletePatient(long patientId) {
		
		List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);
		for (Appointment appointment : appointments) {
			appointmentService.cancelAppointment(appointment.getAppointmentId());
		}
		
//		Patient patient = patientRepository.findById(patientId).orElse(null);
//		User user = patient.getUser();
//		userService.deleteUser(user.getId());
		
		patientRepository.deleteById(patientId);
	}

	public Long getPatientIdByUsername(String username) {
		User user = userRepository.findByUserName(username);
		if (user != null) {
			Patient patient = patientRepository.findByUser(user);
			return patient !=null ? patient.getId() : null;
		}
		return null;
	}
}
