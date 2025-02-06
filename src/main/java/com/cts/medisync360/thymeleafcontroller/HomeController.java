package com.cts.medisync360.thymeleafcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cts.medisync360.entity.Doctor;
import com.cts.medisync360.entity.Patient;
import com.cts.medisync360.entity.User;

@Controller
//@RequestMapping()
public class HomeController {
	
	  @GetMapping("/home") 
	  public String viewHomepage(Model model) {
		  model.addAttribute("user", new User());
		  model.addAttribute("patient", new Patient());
		  model.addAttribute("doctor", new Doctor());
		  return "home";

	}
}
