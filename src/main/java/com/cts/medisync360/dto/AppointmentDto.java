package com.cts.medisync360.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDto {
	private String appointmentDate;
	private long patientId;
	private long doctorId;
	private long slotId;
//	private String reason;
}
