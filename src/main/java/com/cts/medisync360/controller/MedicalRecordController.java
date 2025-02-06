package com.cts.medisync360.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.medisync360.dto.CreateMedicalRecordDto;
import com.cts.medisync360.dto.MedicalRecordResponseDto;
import com.cts.medisync360.dto.UpdateMedicalRecordDto;
import com.cts.medisync360.service.MedicalRecordService;

@RestController
@RequestMapping("/api/medicalrecords")
public class MedicalRecordController {
	
	@Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping("/create")
    public MedicalRecordResponseDto createMedicalRecord(@RequestBody CreateMedicalRecordDto createDTO) {
        return medicalRecordService.createMedicalRecord(createDTO);
    }

    @PutMapping("/update")
    public MedicalRecordResponseDto updateMedicalRecord(@RequestBody UpdateMedicalRecordDto updateDTO) {
        return medicalRecordService.updateMedicalRecord(updateDTO);
    }

    @GetMapping("/patient/{patientId}")
    public List<MedicalRecordResponseDto> getRecordsForPatient(@PathVariable int patientId) {
        return medicalRecordService.getMedicalRecordsForPatient(patientId);
    }

}
