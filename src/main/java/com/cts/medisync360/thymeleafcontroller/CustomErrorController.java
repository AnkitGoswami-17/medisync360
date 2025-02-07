package com.cts.medisync360.thymeleafcontroller;

import org.springframework.boot.web.servlet.error.ErrorController;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;

import jakarta.servlet.RequestDispatcher;

import jakarta.servlet.http.HttpServletRequest;



@Controller

public class CustomErrorController {



  @GetMapping("/error")

  public String handleError(HttpServletRequest request, Model model) {

    String errorMessage = "An unexpected error occurred.";

    model.addAttribute("errorMessage", errorMessage);

    return "error";

  }

}
