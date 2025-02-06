package com.cts.medisync360.entity;

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
@Table(name="slot_details")
public class Slot {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="s_seq")
	@SequenceGenerator(name="s_seq",allocationSize = 1, sequenceName = "s_seq")
	private long id;
	private String startTime;
	private String endTime;
	private boolean isAvailable;
	private String doctorName;
	private String doctorSpeciality;
	private String state;
	
	@ManyToOne
	private Doctor doctor;
	
	@ManyToOne
	private Patient patient;
}
