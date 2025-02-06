package com.cts.medisync360.thymeleafcontroller;

import com.cts.medisync360.dto.CreateMedicalRecordDto;
import com.cts.medisync360.dto.DoctorDto;
import com.cts.medisync360.dto.SlotDto;
import com.cts.medisync360.dto.UpdateMedicalRecordDto;
import com.cts.medisync360.entity.Appointment;
import com.cts.medisync360.entity.Doctor;
import com.cts.medisync360.entity.MedicalRecord;
import com.cts.medisync360.entity.Slot;
import com.cts.medisync360.repository.AppointmentRepository;
import com.cts.medisync360.repository.MedicalRecordRepository;
import com.cts.medisync360.repository.SlotRepository;
import com.cts.medisync360.service.AppointmentService;
import com.cts.medisync360.service.DoctorService;
import com.cts.medisync360.service.MedicalRecordService;
import com.cts.medisync360.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DoctorDashBoardController {

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private SlotService slotService;

	@Autowired
	private SlotRepository slotRepository;

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private MedicalRecordService medicalRecordService;
	
	@Autowired
	private MedicalRecordRepository medicalRecordRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	// Show Create Slot Page
	@GetMapping("/doctor/createslot")
	public String showCreateSlotPage(Model model, Principal principal) {
		// Fetch the logged-in doctor's details
		Long doctorId = doctorService.getDocotorIdByUsername(principal.getName());

		model.addAttribute("doctorId", doctorId);
		return "createslot"; // Thymeleaf template
	}

	// Handle Slot Creation
	@PostMapping("/doctor/createslot")
	public String createSlot(@RequestParam String startTime, @RequestParam String endTime, @RequestParam long doctorId,
			RedirectAttributes redirectAttributes) {

		Doctor doctor = doctorService.getDoctorById(doctorId);
		if (doctor == null) {
			redirectAttributes.addFlashAttribute("error", "Doctor not found!");
			return "redirect:/doctor/createslot";
		}

		SlotDto slot = new SlotDto();

		slot.setDoctorId(doctorId);
		slot.setStartTime(startTime);
		slot.setEndTime(endTime);

		slotService.createSlots(slot);

		redirectAttributes.addFlashAttribute("success", "Slot created successfully!");
		return "redirect:/doctor/createslot";
	}

	@GetMapping("/doctor/createreport")

	public String showCreateReportPage(Model model, Principal principal) {

		Long doctorId = doctorService.getDocotorIdByUsername(principal.getName());

		List<Slot> appointments = slotService.getSlotsByDoctor(doctorId);

		model.addAttribute("appointments", appointments);

		return "createreport";

	}

	// Complete Appointment

	@PostMapping("/doctor/completeappointment")

	public String completeAppointment(@RequestParam("slotId") long slotId, RedirectAttributes redirectAttributes) {

		Appointment appointment = appointmentRepository.findBySlotId(slotId);

		long appId = appointment.getAppointmentId();

		appointmentService.markAppointmentAsCompleted(appId);

		redirectAttributes.addFlashAttribute("completedSlotId", slotId);

		redirectAttributes.addFlashAttribute("success", "Appointment completed successfully.");

		return "redirect:/doctor/createreport";
	}

	@GetMapping("/doctor/writereport")

	public String showWriteReportPage(@RequestParam("appointmentId") long appointmentId, Model model) {

		model.addAttribute("appointmentId", appointmentId);

		return "writereport";

	}

	@GetMapping("/doctor/submitreport")
	public String showWriteReportPage(@RequestParam("appointmentId") long appointmentId, Model model,
			@RequestParam(value = "success", required = false) String success) {
		model.addAttribute("appointmentId", appointmentId);
		if (success != null) {
			model.addAttribute(success, "Report submitted successfully");
		}
		return "writereport";
	}

	// Submit Report

	@PostMapping("/doctor/submitreport")

	public String submitReport(@RequestParam("appointmentId") long appointmentId,

			@RequestParam("diagnosis") String diagnosis,

			@RequestParam("prescription") String prescription,

			@RequestParam("report") String report, RedirectAttributes redirectAttributes) {

		Appointment appointment = appointmentRepository.findBySlotId(appointmentId);

		long appId = appointment.getAppointmentId();

		CreateMedicalRecordDto createDto = new CreateMedicalRecordDto();

		createDto.setAppointmentId(appId);

		createDto.setDiagnosis(diagnosis);

		createDto.setPrescription(prescription);

		createDto.setReport(report);

		medicalRecordService.createMedicalRecord(createDto);
		
		Slot slot = appointment.getSlot();
		
		if (slot != null) {
			slot.setState("report submited");
			slotRepository.save(slot);
		}

		

		redirectAttributes.addFlashAttribute("success", "Report submitted successfully.");

		redirectAttributes.addAttribute("appointmentId", appointmentId);

		return "redirect:/doctor/submitreport";

	}


	 @GetMapping("/doctor/updatereportlist")

	  public String showUpdateReportList(Model model, Principal principal) {

	    // Fetch the logged-in doctor's ID

	    Long doctorId = doctorService.getDocotorIdByUsername(principal.getName());



	    // Fetch all slots for this doctor

	    List<Slot> allSlots = slotService.getSlotsByDoctor(doctorId);



	    // List to store appointments where report has been submitted

	    List<Slot> appointmentsForUpdate = new ArrayList<>();



	    // Iterate through slots and find those with state "reportsubmitted"

	    for (Slot slot : allSlots) {

	      if ("reportsubmitted".equalsIgnoreCase(slot.getState())) {

	        // Fetch appointment using slot ID
	    	  appointmentsForUpdate.add(slot);

	      }

	    }
	    // Add to the model for display

	    model.addAttribute("appointments", appointmentsForUpdate);

	    return "updatereportlist";

	  }



	  /**

	   * GET - Show the update report form for a selected appointment.

	   */

	  @GetMapping("/doctor/updatereportform")

	  public String showUpdateReportForm(@RequestParam("appointmentId") long appointmentId, Model model) {

	    // Retrieve the existing medical record for this appointment
		  
		  Slot slot = slotRepository.getById(appointmentId);
		  
		  long patientId = slot.getPatient().getId();
		  
		  List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);
		  
		  long appId=0;
		  
		  for (Appointment appointment : appointments) {
			  
			  if (appointment.getSlot().getId() == appointmentId) {
				  
				  appId = appointment.getAppointmentId();
			  }
		  }
		  
	    MedicalRecord medicalRecord = medicalRecordRepository.findByAppointmentAppointmentId(appId);
	    
	    UpdateMedicalRecordDto updateDto = new UpdateMedicalRecordDto();
	    
	    updateDto.setRecordId(medicalRecord.getId());
	    updateDto.setDiagnosis(medicalRecord.getDiagnosis());
	    updateDto.setPrescription(medicalRecord.getPrescription());
	    updateDto.setReport(medicalRecord.getReport());

	    model.addAttribute("record", updateDto);

	    return "updatereportform"; 

	  }


	  @PostMapping("/doctor/updatereportform")

	  public String updateReport(@ModelAttribute("record") UpdateMedicalRecordDto updateDto,

	                RedirectAttributes redirectAttributes) {

	    medicalRecordService.updateMedicalRecord(updateDto);

	    redirectAttributes.addFlashAttribute("success", "Report updated successfully.");

	    return "redirect:/doctor/updatereportlist";

	  }
	  
	// Show update profile page

	  @GetMapping("/doctor/updateprofile")

	  public String showUpdateProfileForm(Model model, Principal principal) {
		  
		long doctorId = doctorService.getDocotorIdByUsername(principal.getName());

	    Doctor doctor = doctorService.getDoctorById(doctorId);

	    if (doctor == null) {

	      model.addAttribute("error", "Doctor not found!");

	      return "redirect:/doctor/dashboard";

	    }



	    model.addAttribute("doctor", doctor);

	    return "updatedoctorprofile";

	  }



	  // Handle profile update submission

	  @PostMapping("/doctor/updateprofile")

	  public String updateDoctorProfile(@ModelAttribute DoctorDto doctorDto, Model model,Principal principal) {
		 
		 long doctorId = doctorService.getDocotorIdByUsername(principal.getName()); 
		  
	    Doctor updatedDoctor = doctorService.updateDoctor(doctorId, doctorDto);

	    model.addAttribute("doctor", updatedDoctor);

	    model.addAttribute("success", "Profile updated successfully!");

	    return "updatedoctorprofile";

	  }
	  
	  @GetMapping("/doctor/profile")

	  public String showDoctorProfile(Model model, Principal principal) {

	    Long doctorId = doctorService.getDocotorIdByUsername(principal.getName());

	    Doctor doctor = doctorService.getDoctorById(doctorId);

	    model.addAttribute("doctor", doctor);

	    return "doctor-profile";

	  }

	}

