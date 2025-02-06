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

import com.cts.medisync360.dto.AppointmentDto;
import com.cts.medisync360.entity.Appointment;
import com.cts.medisync360.service.AppointmentService;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {


	@Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public Appointment bookAppointment(@RequestBody AppointmentDto appointmentDto) {
        return appointmentService.bookAppointment(appointmentDto);
    }

    @PutMapping("/{appointmentId}/cancel")
    public Appointment cancelAppointment(@PathVariable long appointmentId) {
        return appointmentService.cancelAppointment(appointmentId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getAppointmentsByDoctor(@PathVariable long doctorId) {
        return appointmentService.getAppointmentsByDoctor(doctorId);
    }

    @GetMapping("/patient/{patientId}")
    public List<Appointment> getAppointmentsByPatient(@PathVariable long patientId) {
        return appointmentService.getAppointmentsByPatient(patientId);
    }

//    @GetMapping("/{appointmentId}")
//    public Appointment getAppointmentById(@PathVariable long appointmentId) {
//        return appointmentService.getAppointmentById(appointmentId);
//    }

}
