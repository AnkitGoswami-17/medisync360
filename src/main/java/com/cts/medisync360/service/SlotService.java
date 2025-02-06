package com.cts.medisync360.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.medisync360.dto.SlotDto;
import com.cts.medisync360.entity.Doctor;
import com.cts.medisync360.entity.Slot;
import com.cts.medisync360.repository.SlotRepository;

@Service
public class SlotService {
	
	@Autowired
	private SlotRepository slotRepository;
	
	@Autowired
	private DoctorService doctorService;
	
//	@Autowired
//	private PatientService patientService;


public Slot createSlots(SlotDto slotRequest) {
        	Doctor doctor = doctorService.getDoctorById(slotRequest.getDoctorId());
        	String doctorName = doctor.getFirstName() + doctor.getLastName();
        	String doctorSpec = doctor.getDocSpec();
        	
            Slot slot = Slot.builder()
                    .doctor(doctor)
                    .startTime(slotRequest.getStartTime())
                    .endTime(slotRequest.getEndTime())
                    .isAvailable(true)
                    .doctorName(doctorName)
                    .doctorSpeciality(doctorSpec)
                    .state("pending")
                    .build();
            
        return slotRepository.save(slot);
    }

    public boolean isSlotAvailable(long slotId) {
        Slot slot = slotRepository.findById(slotId).orElse(null);
        System.out.println(slot);
        if (slot != null) {
            return slot.isAvailable();
        }
        return false;
    }

    public void bookSlot(long slotId, long patientId) {
        Slot slot = slotRepository.findById(slotId).orElse(null);
        if (slot == null) {
            throw new RuntimeException("Slot not found");
        }

        if (!slot.isAvailable()) {
            throw new RuntimeException("Slot is already booked");
        }

        slot.setAvailable(false);
        slotRepository.save(slot);
    }

    public void cancelSlot(long slotId) {
        Slot slot = slotRepository.findById(slotId).orElse(null);
        if (slot == null) {
            throw new RuntimeException("Slot not found");
        }

        if (slot.isAvailable()) {
            throw new RuntimeException("Slot is not booked");
        }

        slot.setAvailable(true);
        slot.setPatient(null);
        slotRepository.save(slot);
    }

    public List<Slot> getSlotsByDoctor(long doctorId) {
        return slotRepository.findByDoctorId(doctorId);
    }
    
    public List<Slot> getAllAvailableSlots() {
    	return slotRepository.findAvailableSlots();
//        List<Slot> allSlots = slotRepository.findAll();
//        List<Slot> availableSlots = new ArrayList<>();
//        System.out.println(availableSlots);
//        for (Slot slot : allSlots) {
//            if (slot.isAvailable()) {
//                availableSlots.add(slot);
//            }
//        }
//        return availableSlots;
    }

    public List<Slot> getAvailableSlotsByDoctor(long doctorId) {
        return slotRepository.findByDoctorIdAndIsAvailableTrue(doctorId);
    }

    public void deleteSlotsByDoctor(long doctorId) {
        slotRepository.deleteByDoctorId(doctorId);
    }
    
    public Slot getSlotById(long sId) {
    	return slotRepository.findById(sId).orElseThrow(
    			()-> new RuntimeException("No slots found with ID: " + sId));
   
    }
    

}
