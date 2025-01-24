package com.tka.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		return appointmentService.addAppointment(appointment);

	}

	@GetMapping("/get-all")
	public List<Appointment> getAllAppointment() {
		return appointmentService.getAllAppointment();
	}

	@GetMapping("/get-by-id/{aId}")
	public Object getAppointmentById(@PathVariable Long aId) {
		return appointmentService.getAppointmentById(aId);

	}

	@GetMapping("/get-by-date")
	public Object getAppointmentByDate(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime appointmentDate) {
		return appointmentService.getAppointmentByDate(appointmentDate);
	}

	@PutMapping("/update-appointment")
	public String updateAppointment(@RequestBody Appointment appointment) {
		return appointmentService.updateAppointment(appointment);

	}

}
