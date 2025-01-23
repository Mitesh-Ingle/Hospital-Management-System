package com.tka.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "departments")
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long dId;

	@Column(unique = true)
	private String dName;

	private String dDescription;

	@OneToMany(mappedBy = "department")
	@JsonManagedReference
	private List<Doctor> doctors;

	@OneToMany(mappedBy = "department")
	@JsonManagedReference
	private List<Patient> patients;

	public Long getdId() {
		return dId;
	}

	public void setdId(Long dId) {
		this.dId = dId;
	}

	public String getdName() {
		return dName;
	}

	public void setdName(String dName) {
		this.dName = dName;
	}

	public String getdDescription() {
		return dDescription;
	}

	public void setdDescription(String dDescription) {
		this.dDescription = dDescription;
	}

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public Department(Long dId, String dName, String dDescription, List<Doctor> doctors, List<Patient> patients) {
		super();
		this.dId = dId;
		this.dName = dName;
		this.dDescription = dDescription;
		this.doctors = doctors;
		this.patients = patients;
	}

	public Department() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Department [dId=" + dId + ", dName=" + dName + ", dDescription=" + dDescription + ", doctors=" + doctors
				+ ", patients=" + patients + "]";
	}

}
