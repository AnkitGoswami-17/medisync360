package com.cts.medisync360.thymeleafcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/auth")
public class DocController {
	
	@GetMapping("/doctor") 
	public String openDoctor() {
		return "doctor";
	}

}
