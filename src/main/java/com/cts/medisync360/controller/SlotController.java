package com.cts.medisync360.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.medisync360.dto.SlotDto;
import com.cts.medisync360.entity.Slot;
import com.cts.medisync360.service.SlotService;

@RestController
@RequestMapping("/api/slots")
public class SlotController {
	
	@Autowired
	private SlotService slotService;

	@PostMapping("/createslot")
	public Slot createSlots(@RequestBody SlotDto slotRequests) {
		return slotService.createSlots(slotRequests);
	}

	@GetMapping("/{slotId}/available")
	public boolean isSlotAvailable(@PathVariable long slotId) {
		return slotService.isSlotAvailable(slotId);
	}

//	@PutMapping("/{slotId}/book/{patientId}")
//	public void bookSlot(@PathVariable long slotId, @PathVariable long patientId) {
//		slotService.bookSlot(slotId, patientId);
//	}

	@PutMapping("/{slotId}/cancel")
	public void cancelSlot(@PathVariable long slotId) {
		slotService.cancelSlot(slotId);
	}

	@GetMapping("/doctor/{doctorId}")
	public List<Slot> getSlotsByDoctor(@PathVariable long doctorId) {
		return slotService.getSlotsByDoctor(doctorId);
	}

	@GetMapping("/doctor/{doctorId}/available")
	public List<Slot> getAvailableSlotsByDoctor(@PathVariable long doctorId) {
		return slotService.getAvailableSlotsByDoctor(doctorId);
	}

	@DeleteMapping("/doctor/{doctorId}")
	public void deleteSlotsByDoctor(@PathVariable long doctorId) {
		slotService.deleteSlotsByDoctor(doctorId);
	}

	@GetMapping("/{slotId}")
	public Slot getSlotById(@PathVariable long slotId) {
		return slotService.getSlotById(slotId);
	}

}
