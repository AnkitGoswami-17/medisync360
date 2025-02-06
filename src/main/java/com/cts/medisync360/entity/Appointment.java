package com.cts.medisync360.entity;

import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
@Table(name="appointment_details")
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="app_seq")
	@SequenceGenerator(name="app_seq",allocationSize = 1, sequenceName = "app_seq")
	private long appointmentId;
	private String status;
//	private Date appointmentDate;
//	private String reason;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Patient patient;

	@ManyToOne(cascade = CascadeType.ALL)
	private Doctor doctor;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Slot slot;
	
	
	
}
