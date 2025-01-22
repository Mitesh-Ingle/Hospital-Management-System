package com.tka.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "appointments")
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long aId;

	@ManyToOne
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;

	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;
	
	 @JsonFormat(pattern = "dd MMM yyyy hh:mm a", shape = JsonFormat.Shape.STRING)
		private Date appointmentDate;

	public Appointment(Long aId, Doctor doctor, Patient patient, Date appointmentDate) {
		super();
		this.aId = aId;
		this.doctor = doctor;
		this.patient = patient;
		this.appointmentDate = appointmentDate;
	}

	public Appointment(Doctor doctor, Patient patient, Date appointmentDate) {
		super();
		this.doctor = doctor;
		this.patient = patient;
		this.appointmentDate = appointmentDate;
	}

	public Appointment() {
		// TODO Auto-generated constructor stub
	}

	public Long getaId() {
		return aId;
	}

	public void setaId(Long aId) {
		this.aId = aId;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	@Override
	public String toString() {
		return "Appointment [aId=" + aId + ", doctor=" + doctor + ", patient=" + patient + ", appointmentDate="
				+ appointmentDate + "]";
	}

}
