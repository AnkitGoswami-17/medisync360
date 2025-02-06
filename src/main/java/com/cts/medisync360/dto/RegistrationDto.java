package com.cts.medisync360.dto;

import java.sql.Date;

import com.cts.medisync360.entity.UserType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {
	private String userPassword;
	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private String contactNo;
	//patient field
	private String city;
	private String gender;
	private String age;
//	private Date dob;
	//doctor field
	private String docSpec;
	private String consultancyFees;
	private UserType userType;
}
