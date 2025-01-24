package com.tka.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tka.entity.Doctor;
import com.tka.service.DoctorService;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

	@Autowired
	DoctorService doctorService;

	@PostMapping("/add-doctor")
	public String addDoctor(@RequestBody Doctor doctor) {
		System.err.println(doctor);
		System.err.println("In AddDoctor Controller");
		return doctorService.addDoctor(doctor);
	}

	@GetMapping("/get-all-doctors")
	public List<Doctor> getAllDoctor() {
		System.err.println("In getAll Doctor Controller");
		return doctorService.getAllDoctor();
	}

	@GetMapping("/get-by-id/{id}")
	public Object getDoctorById(@PathVariable Long id) {
		return doctorService.getDoctorById(id);
	}
	
	@GetMapping("/get-by-name")
	public Object getDoctorByName(@RequestParam String name) {
		return doctorService.getDoctorByName(name);
	}
}
