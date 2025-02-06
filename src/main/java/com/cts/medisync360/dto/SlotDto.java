package com.cts.medisync360.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlotDto {
	private long doctorId;
	private String startTime;
	private String endTime;
}
