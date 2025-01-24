package com.tka.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tka.entity.Patient;
import com.tka.service.PatientService;

@RestController
@RequestMapping("/patients")
public class PatientController {

	@Autowired
	PatientService patientService;

	@PostMapping("/add")
	public String addPatient(@RequestBody Patient patient) {
		System.err.println("In AddPatient Controller");
		return patientService.addPatient(patient);
	}

	@GetMapping("/get-patients")
	public List<Patient> getPatient() {
		return patientService.getPatient();
	}

	@GetMapping("/get-by-id/{pId}")
	public Object getPatientById(@PathVariable Long pId) {
		return patientService.getPatientById(pId);
	}

	@GetMapping("/get-by-name")
	public Object getPatientByName(@RequestParam String pName) {
		return patientService.getPatientByName(pName);
	}

	@PutMapping("/update-patient")
	public String updatePatient(@RequestBody Patient patient) {
		return patientService.updatePatient(patient);
	}
}
