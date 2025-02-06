package com.cts.medisync360.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medical_record")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="rec_seq")
	@SequenceGenerator(name="rec_seq",allocationSize = 1, sequenceName = "rec_seq")
    private long id;

    @OneToOne(cascade=CascadeType.ALL)
    private Appointment appointment;

    @ManyToOne(cascade=CascadeType.ALL)
    private Doctor doctor;

    @ManyToOne(cascade=CascadeType.ALL)
    private Patient patient;
    
    private String diagnosis;
    private String prescription;
    private String report;
}
