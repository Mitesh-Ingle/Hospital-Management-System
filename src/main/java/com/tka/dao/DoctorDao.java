package com.tka.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tka.entity.Department;
import com.tka.entity.Doctor;

@Repository
public class DoctorDao {

	@Autowired
	SessionFactory factory;

	Department department;

	public String addDoctor(Doctor doctor) {
		System.err.println("In doctor dao");
		System.err.println(doctor);
		System.err.println(1);
		if (doctor.getName() == null || doctor.getName().isEmpty()) {
			return "Provide Name";
		}
		if (doctor.getSpecialty() == null || doctor.getSpecialty().isEmpty()) {
			return "Provide Speciality";
		}
		if (doctor.getContactNumber() == null || doctor.getContactNumber().isEmpty()) {
			return "Provide Contact Number";
		}
		if (doctor.getEmail() == null || doctor.getEmail().isEmpty()) {
			return "Provide Email";
		}
		Session session = factory.openSession();
		session.beginTransaction();
		System.err.println(2);
		Criteria contactNumberCriteria = session.createCriteria(Doctor.class);
		contactNumberCriteria.add(Restrictions.eq("contactNumber", doctor.getContactNumber()));
		List<Doctor> contactList = contactNumberCriteria.list();
		System.err.println(3);
		Criteria emailCriteria = session.createCriteria(Doctor.class);
		emailCriteria.add(Restrictions.eq("email", doctor.getEmail()));
		List<Doctor> emailList = emailCriteria.list();
		System.err.println(4);
		if (!contactList.isEmpty() && !emailList.isEmpty()) {
			System.err.println(5);
			session.close();
			System.err.println(6);
			return "Both Email and Mobile Number already exist please provide new ";
		} else if (!contactList.isEmpty()) {
			System.err.println(7);
			session.close();
			return "Contact Number already present : " + doctor.getContactNumber() + " please provide different one ";
		} else if (!emailList.isEmpty()) {
			System.err.println(8);
			session.close();
			return "Email : " + doctor.getEmail() + " already exist Please Provide different Email";
		}
		session.saveOrUpdate(doctor);
		System.err.println(9);
		session.getTransaction().commit();
		System.err.println(10);
		session.close();
		return "Doctor added successfully.";

	}

	public List<Doctor> getAllDoctor() {
		List<Doctor> doctorsList = new ArrayList<>();
		try {
			Session session = factory.openSession();
			Criteria criteria = session.createCriteria(Doctor.class);
			doctorsList = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doctorsList;
	}

}