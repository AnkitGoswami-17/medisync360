package com.cts.medisync360.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMedicalRecordDto {
	private long appointmentId;
	private String diagnosis;
	private String prescription;
	private String report;
}
