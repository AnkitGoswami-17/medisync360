package com.cts.medisync360.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.medisync360.dto.PatientDto;
import com.cts.medisync360.entity.Patient;
import com.cts.medisync360.service.PatientService;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/register")
    public Patient registerPatient(@RequestBody PatientDto patientDto) {
        return patientService.registerPatient(patientDto);
    }

    @GetMapping("/{patientId}")
    public Patient getPatientById(@PathVariable long patientId) {
        return patientService.getPatientById(patientId);
    }

//    @GetMapping("/{patientId}/appointments")
//    public List<Appointment> getAppointmentsForPatient(@PathVariable long patientId) {
//        return patientService.getAppointmentsForPatient(patientId);
//    }

//    @GetMapping("/doctor/{doctorId}/available-slots")
//    public List<Slot> getAvailableSlotsForDoctor(@PathVariable long doctorId) {
//        return patientService.getAvailableSlotsForDoctor(doctorId);
//    }


    @PutMapping("/update/{patientId}")
    public Patient updatePatient(@PathVariable long patientId, @RequestBody PatientDto updatedPatient) {
        return patientService.updatePatient(patientId, updatedPatient);
    }


    @DeleteMapping("/delete/{patientId}")
    public void deletePatient(@PathVariable long patientId) {
        patientService.deletePatient(patientId);
    }


}
