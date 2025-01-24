package com.tka.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tka.dao.PatientDao;
import com.tka.entity.Patient;

@Service
public class PatientService {

	@Autowired
	PatientDao patientDao;

	public String addPatient(Patient patient) {
		System.err.println("In AddPatient Service");
		return patientDao.addPatient(patient);
	}

	public List<Patient> getPatient() {
		return patientDao.getPatient();
	}

	public Object getPatientById(Long pId) {
		return patientDao.getPatientById(pId);
	}

	public Object getPatientByName(String pName) {
		return patientDao.getPatientByName(pName);
	}

	public String updatePatient(Patient patient) {
		return patientDao.updatePatient(patient);
	}

}
