package com.cts.medisync360.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {
	private String userPassword;
	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private String contactNo;
	private String city;
	private String gender;
//	private Date dob;
	private String age;
}
