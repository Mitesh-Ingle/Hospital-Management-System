package com.tka.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "patients")
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pId;

	@Column(nullable = false)
	private String pName;

	@Column(nullable = false)
	private String pDisease;

	@Column(nullable = false)
	private String pContact;

	@Column(nullable = false)
	private String pAddress;

	@Column(nullable = false)
	private String pEmail;

	@ManyToOne(optional = true, cascade = CascadeType.ALL, fetch =  FetchType.EAGER)
	@JoinColumn(name = "department_id")
	@JsonBackReference
	private Department department;

	public Patient(Long pId, String pName, String pDisease, String pContact, String pAddress, String pEmail,
			Department department) {
		super();
		this.pId = pId;
		this.pName = pName;
		this.pDisease = pDisease;
		this.pContact = pContact;
		this.pAddress = pAddress;
		this.pEmail = pEmail;
		this.department = department;
	}

	public Patient() {
		// TODO Auto-generated constructor stub
	}

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getpDisease() {
		return pDisease;
	}

	public void setpDisease(String pDisease) {
		this.pDisease = pDisease;
	}

	public String getpContact() {
		return pContact;
	}

	public void setpContact(String pContact) {
		this.pContact = pContact;
	}

	public String getpAddress() {
		return pAddress;
	}

	public void setpAddress(String pAddress) {
		this.pAddress = pAddress;
	}

	public String getpEmail() {
		return pEmail;
	}

	public void setpEmail(String pEmail) {
		this.pEmail = pEmail;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Patient(String pName, String pDisease, String pContact, String pAddress, String pEmail,
			Department department) {
		super();
		this.pName = pName;
		this.pDisease = pDisease;
		this.pContact = pContact;
		this.pAddress = pAddress;
		this.pEmail = pEmail;
		this.department = department;
	}

	@Override
	public String toString() {
		return "Patient [pId=" + pId + ", pName=" + pName + ", pDisease=" + pDisease + ", pContact=" + pContact
				+ ", pAddress=" + pAddress + ", pEmail=" + pEmail + ", department=" + department + "]";
	}

}
