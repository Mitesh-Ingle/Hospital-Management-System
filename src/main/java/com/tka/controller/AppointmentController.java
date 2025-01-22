package com.tka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tka.entity.Appointment;
import com.tka.service.AppointmentService;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

	@Autowired
	AppointmentService appointmentService;
	
	@PostMapping("/add-appointment")
	public String addAppointment(@RequestBody Appointment appointment) {
		System.err.println("In add Appointment Controller method");
		return appointmentService.addAppointment(appointment) ;
		
	}
}