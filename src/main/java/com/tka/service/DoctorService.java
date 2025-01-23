package com.tka.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tka.dao.DoctorDao;
import com.tka.entity.Doctor;

@Service
public class DoctorService {

	
	@Autowired
	DoctorDao doctorDao;

	public String addDoctor(Doctor doctor) {
		System.err.println("In Doctor Service");
		return doctorDao.addDoctor(doctor);
	}
	
	public List<Doctor> getAllDoctor() {
		return doctorDao.getAllDoctor();
	}

	public Object getDoctorById(Long id) {
		return doctorDao.getDoctorById(id);
	}
	
}
