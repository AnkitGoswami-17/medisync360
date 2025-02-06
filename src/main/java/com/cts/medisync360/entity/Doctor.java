package com.cts.medisync360.entity;

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
@Table(name="doctor_details")
public class Doctor {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="doc_seq")
	@SequenceGenerator(name="doc_seq",allocationSize = 1, sequenceName = "doc_seq")
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private String contactNo;
	private String docSpec;
	private String consultancyFees;
	
	@OneToOne(cascade=CascadeType.ALL)
//	@JoinColumn(name="user_id")
	private User user;
}
