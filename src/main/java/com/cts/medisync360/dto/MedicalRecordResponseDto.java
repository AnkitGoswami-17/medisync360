package com.cts.medisync360.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordResponseDto {
	private long recordId;
    private String doctorName;
    private String patientName;
    private String diagnosis;
    private String prescription;
    private String report;
}
