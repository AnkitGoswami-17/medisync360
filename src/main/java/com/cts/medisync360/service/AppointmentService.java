package com.cts.medisync360.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.medisync360.dto.AppointmentDto;
import com.cts.medisync360.entity.Appointment;
import com.cts.medisync360.entity.Doctor;
import com.cts.medisync360.entity.Patient;
import com.cts.medisync360.entity.Slot;
import com.cts.medisync360.repository.AppointmentRepository;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private SlotService slotService;

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private PatientService patientService;

	public Appointment bookAppointment(AppointmentDto appointmentDto) {
		
		if (!slotService.isSlotAvailable(appointmentDto.getSlotId())) {

//			System.out.println(slotService.isSlotAvailable(appointmentDto.getSlotId()));

			throw new RuntimeException("Slot is not available");
		}

		Doctor doctor = doctorService.getDoctorById(appointmentDto.getDoctorId());
		Slot slot = slotService.getSlotById(appointmentDto.getSlotId());
		Patient patient = patientService.getPatientById(appointmentDto.getPatientId());

		if (doctor.getId() != slot.getDoctor().getId()) {
			
			throw new RuntimeException("invalid doctor id");
		}

			slot.setPatient(patient);

			System.out.println(doctor);
			System.out.println(slot);
			System.out.println(patient);

			Appointment appointment = Appointment.builder()
					.doctor(doctor)
					.patient(patient)
					.slot(slot)
//					.appointmentDate(appointmentDto.getAppointmentDate())
					.status("BOOKED")
					.build();

			System.out.println(appointment);

			slotService.bookSlot(appointmentDto.getSlotId(), appointmentDto.getPatientId());

			return appointmentRepository.save(appointment);
	}

	public Appointment cancelAppointment(long appointmentId) {
		Appointment appointment = appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new RuntimeException("Appointment not found"));

		slotService.cancelSlot(appointment.getSlot().getId());

		appointment.setStatus("CANCELLED");
		return appointmentRepository.save(appointment);
	}
	
	public void markAppointmentAsCompleted(Long appointmentId) {
		Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(
				()-> new RuntimeException("Appointment not found"));
		appointment.setStatus("COMPLETED");
		appointmentRepository.save(appointment);
	}

	public List<Appointment> getAppointmentsByDoctor(long doctorId) {
		return appointmentRepository.findByDoctorId(doctorId);
	}

	public List<Appointment> getAppointmentsByPatient(long patientId) {
		return appointmentRepository.findByPatientId(patientId);
	}

//	public Appointment getAppointmentById(long appointmentId) {
//		return appointmentRepository.findById(appointmentId)
//				.orElseThrow(() -> new RuntimeException("Appointment not found"));
//	}
}
