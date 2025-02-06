package com.cts.medisync360.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.medisync360.dto.CreateMedicalRecordDto;
import com.cts.medisync360.dto.MedicalRecordResponseDto;
import com.cts.medisync360.dto.UpdateMedicalRecordDto;
import com.cts.medisync360.entity.Appointment;
import com.cts.medisync360.entity.MedicalRecord;
import com.cts.medisync360.entity.Patient;
import com.cts.medisync360.repository.AppointmentRepository;
import com.cts.medisync360.repository.MedicalRecordRepository;

@Service
public class MedicalRecordService {

	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	public MedicalRecordResponseDto createMedicalRecord(CreateMedicalRecordDto createDTO) {

		Appointment appointment = appointmentRepository.findById(createDTO.getAppointmentId())
				.orElseThrow(() -> new RuntimeException("Appointment not found"));

		MedicalRecord existingRecord = medicalRecordRepository.findByAppointment(appointment);

		if (existingRecord != null) {
			throw new RuntimeException("medical record already exists for this appointment");
		}
		
		MedicalRecord record = new MedicalRecord();
		record.setAppointment(appointment);
		record.setDoctor(appointment.getDoctor());
		record.setPatient(appointment.getPatient());
		record.setDiagnosis(createDTO.getDiagnosis());
		record.setPrescription(createDTO.getPrescription());
		record.setReport(createDTO.getReport());

		MedicalRecord savedRecord = medicalRecordRepository.save(record);

		return convertToDto(savedRecord);
	}

	public MedicalRecordResponseDto updateMedicalRecord(UpdateMedicalRecordDto updateDTO) {
		MedicalRecord record = medicalRecordRepository.findById(updateDTO.getRecordId())
				.orElseThrow(() -> new RuntimeException("Medical record not found."));

		record.setDiagnosis(updateDTO.getDiagnosis());
		record.setPrescription(updateDTO.getPrescription());
		record.setReport(updateDTO.getReport());

		MedicalRecord updatedRecord = medicalRecordRepository.save(record);
		return convertToDto(updatedRecord);
	}

	public List<MedicalRecordResponseDto> getMedicalRecordsForPatient(long patientId) {
		
		Patient patient = new Patient();
		
		patient.setId(patientId);

		List<MedicalRecord> records = medicalRecordRepository.findByPatient(patient);

		List<MedicalRecordResponseDto> responseDtos = new ArrayList<MedicalRecordResponseDto>();

		for (MedicalRecord record : records) {

			responseDtos.add(convertToDto(record));
		}
		return responseDtos;
	}

	private MedicalRecordResponseDto convertToDto(MedicalRecord record) {
		MedicalRecordResponseDto responseDTO = new MedicalRecordResponseDto();
		responseDTO.setRecordId(record.getId());
		responseDTO.setDoctorName(record.getDoctor().getFirstName() + " " + record.getDoctor().getLastName());
		responseDTO.setPatientName(record.getPatient().getFirstName() + " " + record.getPatient().getLastName());
		responseDTO.setDiagnosis(record.getDiagnosis());
		responseDTO.setPrescription(record.getPrescription());
		responseDTO.setReport(record.getReport());
		return responseDTO;
	}
}
