package com.cts.medisync360.thymeleafcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	  
	  @GetMapping("/about")
	  public String viewAboutPage() {
		  return "about";
	  }
	  
	  @GetMapping("/contact")
	  public String viewContactPage() {
		  return "contact";
	  }
	  
	  @PostMapping("/contact")
	  public String handleContactForm(RedirectAttributes redirectAttributes) {
		  redirectAttributes.addFlashAttribute("successMessage","Thank you! Your message has been received.");
		  return "redirect:/contact";
	  }
}
