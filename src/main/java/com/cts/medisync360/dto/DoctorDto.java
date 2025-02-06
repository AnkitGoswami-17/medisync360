package com.cts.medisync360.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {
	private String userPassword;
	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private String contactNo;
	private String docSpec;
	private String consultancyFees;
}
