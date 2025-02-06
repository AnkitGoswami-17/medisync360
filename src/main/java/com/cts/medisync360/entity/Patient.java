package com.cts.medisync360.entity;

import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="patient_details")
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="p_seq")
	@SequenceGenerator(name = "p_seq",allocationSize = 1, sequenceName = "p_seq")
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String contactNo;
	private String address;
	private String city;
	private String gender;
	private String age;
//	private Date dob;
	
	@OneToOne(cascade=CascadeType.ALL)
//	@JoinColumn(name="user_id")
	private User user;
}
