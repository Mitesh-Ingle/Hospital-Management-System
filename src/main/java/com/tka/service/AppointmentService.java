package com.tka.service;

import java.time.LocalDateTime;
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


	public Object getAppointmentByDate(LocalDateTime appointmentDate) {
		return appointmentDao.getAppointmentByDate(appointmentDate);
	}


	public String updateAppointment(Appointment appointment) {
		return appointmentDao.updateAppointment(appointment);
	}


	public Object deleteAppointment(Long aId) {
		return appointmentDao.deleteAppointment(aId);
	}
	
	
	
}
