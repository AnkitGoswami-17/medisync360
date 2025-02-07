package com.cts.medisync360.thymeleafcontroller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cts.medisync360.dto.AppointmentDto;
import com.cts.medisync360.dto.MedicalRecordResponseDto;
import com.cts.medisync360.dto.PatientDto;
import com.cts.medisync360.entity.Appointment;
import com.cts.medisync360.entity.Patient;
import com.cts.medisync360.entity.Slot;
import com.cts.medisync360.service.AppointmentService;
import com.cts.medisync360.service.MedicalRecordService;
import com.cts.medisync360.service.PatientService;
import com.cts.medisync360.service.SlotService;

import java.security.Principal;
import java.util.List;

@Controller
public class PatientDashboardController {

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private SlotService slotService;

	@Autowired
	private PatientService patientService;
	
	@Autowired
	private MedicalRecordService medicalRecordService;

	@GetMapping("/appointments/book")

	public String showAvailableSlots(Model model, Principal principal) {

		Long patientId = patientService.getPatientIdByUsername(principal.getName());
		System.out.println(patientId);

		List<Slot> availableSlots = slotService.getAllAvailableSlots();
		System.out.println(availableSlots);

		model.addAttribute("availableSlots", availableSlots); // Fetch all available slots

		model.addAttribute("loggedInPatientId", patientId);

		return "book-appointment";

	}

	@PostMapping("/appointments/book")

	public String bookAppointment(@RequestParam("slotId") Long slotId,

			@RequestParam("doctorId") Long doctorId,

			@RequestParam("patientId") Long patientId,RedirectAttributes redirectAttributes) {

		AppointmentDto appointmentDto = new AppointmentDto();

		appointmentDto.setSlotId(slotId);

		appointmentDto.setDoctorId(doctorId);

		appointmentDto.setPatientId(patientId);

		appointmentService.bookAppointment(appointmentDto);
		
		redirectAttributes.addFlashAttribute("successMessage","Appointment booked successfully!");

		return "redirect:/appointments/book";

	}

	@GetMapping("/appointments/view")
	public String viewAppointments(Model model, Principal principal) {

		Long patientId = patientService.getPatientIdByUsername(principal.getName());

		List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);

		model.addAttribute("appointments", appointments);

		model.addAttribute("hasAppointments", !appointments.isEmpty()); // To check if data exists

		return "view-appointments"; // Returns Thymeleaf page

	}
	
	@PostMapping("/appointments/cancel")
	public String cancelAppointment(@RequestParam("appointmentId") Long appointmentId,RedirectAttributes redirectAttributes) {
		appointmentService.cancelAppointment(appointmentId);
		redirectAttributes.addAttribute("cancelSuccess",true);
		return "redirect:/appointments/view";
	}
	
	@GetMapping("/appointments/myreports") 
	public String viewReport(Principal principal,Model model) {
		
		long patientId = patientService.getPatientIdByUsername(principal.getName());
		
		List<MedicalRecordResponseDto> record = medicalRecordService.getMedicalRecordsForPatient(patientId);
		
		model.addAttribute("records",record);
		
		return "view-report";
		
	}
	
	 @GetMapping("/patient/update-profile")

	  public String showUpdateProfileForm(Model model,Principal principal) {

		 long patientId = patientService.getPatientIdByUsername(principal.getName());

	    Patient patient = patientService.getPatientById(patientId);


	    PatientDto patientDto = new PatientDto();
	    
	    patientDto.setFirstName(patient.getFirstName());
	    patientDto.setLastName(patient.getLastName());
	    patientDto.setEmail(patient.getEmail());
	    patientDto.setContactNo(patient.getContactNo());
	    patientDto.setAddress(patient.getAddress());
	    patientDto.setCity(patient.getCity());
	    patientDto.setGender(patient.getGender());
	    patientDto.setAge(patient.getAge());

	    model.addAttribute("patient", patientDto);

	    return "update-patientprofile";

	  }



	  @PostMapping("/patient/update-profile")

	  public String updateProfile(@ModelAttribute("patient") PatientDto updatedPatient,Principal principal,RedirectAttributes redirectAttributes) {

		 long patientId = patientService.getPatientIdByUsername(principal.getName());

	    patientService.updatePatient(patientId, updatedPatient);
	    
	    redirectAttributes.addFlashAttribute("successMessage","Profile Updated Successfully!");

	    return "redirect:/patient/update-profile";

	  }
	  
	  @GetMapping("/patient/profile")

	  public String showPatientProfile(Model model, Principal principal) {

	    Long patientId = patientService.getPatientIdByUsername(principal.getName());

	    Patient patient = patientService.getPatientById(patientId);

	    model.addAttribute("patient", patient);

	    return "patient-profile";

	  }
}
