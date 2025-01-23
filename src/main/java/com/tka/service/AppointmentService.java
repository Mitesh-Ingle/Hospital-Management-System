package com.tka.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tka.dao.AppointmentDao;
import com.tka.entity.Appointment;

@Service
public class AppointmentService {

	@Autowired
	AppointmentDao appointmentDao;
	
	
	public String addAppointment(Appointment appointment) {
		System.err.println("In add Appointment service method");
		return appointmentDao.addAppointment(appointment);
	}


	public List<Appointment> getAllAppointment() {
		return appointmentDao.getAllAppointment();
	}


	public Object getAppointmentById(Long aId) {
		return appointmentDao.getAppointmentById(aId);
	}
	
	
	
}
