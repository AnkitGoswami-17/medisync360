package com.cts.medisync360.thymeleafcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cts.medisync360.dto.DoctorDto;
import com.cts.medisync360.dto.LoginRequestDto;
import com.cts.medisync360.dto.PatientDto;
import com.cts.medisync360.dto.RegistrationDto;
import com.cts.medisync360.entity.User;

import com.cts.medisync360.entity.UserType;

import com.cts.medisync360.service.DoctorService;

import com.cts.medisync360.service.PatientService;

import com.cts.medisync360.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private PatientService patientService;

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/register")
	public String showRegistrationPage(Model model) {

		model.addAttribute("registrationDto", new RegistrationDto());

		return "registration";

	}

	@PostMapping("/register")

	public String registerUser(@ModelAttribute("registrationDto") RegistrationDto registrationDto,

	              RedirectAttributes redirectAttributes) {

	  System.out.println("Received Registration Data: " + registrationDto);



	  if (registrationDto.getUserType() == null) {

	    System.out.println("UserType is NULL! Fix the form binding.");

	  }



	  if (registrationDto.getUserType() == UserType.PATIENT) {

	    System.out.println("Registering as a Patient");



	    PatientDto patientDto = new PatientDto();

	    patientDto.setUserPassword(registrationDto.getUserPassword());
	    patientDto.setFirstName(registrationDto.getFirstName());
	    patientDto.setLastName(registrationDto.getLastName());
	    patientDto.setCity(registrationDto.getCity());
	    patientDto.setAddress(registrationDto.getAddress());
	    patientDto.setContactNo(registrationDto.getContactNo());
//	    patientDto.setDob(registrationDto.getDob());
	    patientDto.setGender(registrationDto.getGender());
	    patientDto.setEmail(registrationDto.getEmail());



	    System.out.println("Patient DTO: " + patientDto);
	    
	    patientService.registerPatient(patientDto);



	  } else if (registrationDto.getUserType() == UserType.DOCTOR) {
	    System.out.println("Registering as a Doctor");



	    DoctorDto doctorDto = new DoctorDto();

	    doctorDto.setUserPassword(registrationDto.getUserPassword());
	    doctorDto.setFirstName(registrationDto.getFirstName());
	    doctorDto.setLastName(registrationDto.getLastName());
	    doctorDto.setEmail(registrationDto.getEmail());
	    doctorDto.setAddress(registrationDto.getAddress());
	    doctorDto.setContactNo(registrationDto.getContactNo());
	    doctorDto.setDocSpec(registrationDto.getDocSpec());
	    doctorDto.setConsultancyFees(registrationDto.getConsultancyFees());



	    System.out.println("Doctor DTO: " + doctorDto);
	    doctorService.registerDoctor(doctorDto);

	  }



	  redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please login.");

	  return "redirect:/auth/login";

	}

	@GetMapping("/login")
	public String showLoginPage(Model model) {

		model.addAttribute("loginRequestDto", new LoginRequestDto());

		return "login";

	}

	/*
	 * @PostMapping("/login") public String login(@ModelAttribute LoginRequestDto
	 * loginRequestDto, RedirectAttributes redirectAttributes) {
	 * 
	 * try {
	 * 
	 * Authentication auth = authenticationManager.authenticate(
	 * 
	 * new UsernamePasswordAuthenticationToken(loginRequestDto.getUserName(),
	 * loginRequestDto.getPassword())
	 * 
	 * );
	 * 
	 * SecurityContextHolder.getContext().setAuthentication(auth);
	 * 
	 * User user = userService.getUserByName(loginRequestDto.getUserName());
	 * 
	 * if (user.getUserType() == UserType.DOCTOR) {
	 * 
	 * return "redirect:/auth/doctor";
	 * 
	 * } else {
	 * 
	 * return "redirect:/auth/patient";
	 * 
	 * }
	 * 
	 * } catch (Exception e) {
	 * 
	 * redirectAttributes.addFlashAttribute("error",
	 * "Invalid username or password!"); return "redirect:/auth/login?error";
	 * 
	 * }
	 */

//	}

	@GetMapping("/logout")

	public String logout() {

		SecurityContextHolder.clearContext();

		return "redirect:/home"; // Redirect to home page after logout

	}

}