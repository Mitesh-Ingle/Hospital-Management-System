package com.tka.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.sql.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tka.controller.DoctorController;
import com.tka.entity.Appointment;
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

	public Object getDoctorById(Long id) {
		Session session = factory.openSession();
		Doctor doctor = session.get(Doctor.class, id);
		if (doctor != null) {
			return doctor;
		} else {
			return "Doctor with Id : " + id + "  " + "not found Provide valid Id";
		}

	}

	public Object getDoctorByName(String name) {
		Session session = factory.openSession();
		String hql = "FROM Doctor WHERE name = :name";
		Query<Doctor> query = session.createQuery(hql, Doctor.class);
		query.setParameter("name", name);

		List<Doctor> doctors = query.list(); // Fetch all doctors with the given name

		if (!doctors.isEmpty()) {
			return doctors; // Return the list of doctors
		} else {
			return "No doctors found with the provided name: " + name;
		}
	}

	public String updateDoctor(Doctor doctor) {

		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			Doctor existingDoc = session.get(Doctor.class, doctor.getId());
			if (existingDoc != null) {

				if (existingDoc.getName().equals(doctor.getName())
						&& existingDoc.getSpecialty().equals(doctor.getSpecialty())
						&& existingDoc.getContactNumber().equals(doctor.getContactNumber())
						&& existingDoc.getEmail().equals(doctor.getEmail())) {
					return "No changes made, Name , speciality, contactnumber, email are same ";

				}

				existingDoc.setName(doctor.getName());
				existingDoc.setSpecialty(doctor.getSpecialty());
				existingDoc.setContactNumber(doctor.getContactNumber());
				existingDoc.setEmail(doctor.getEmail());

				session.update(existingDoc);
				transaction.commit();
				return "Doctor updated Successfully";

			} else {
				return "Doctor not present with provided Id : " + doctor.getId() + " Enter Valid Id";
			}
		} catch (Exception e) {
			transaction.rollback(); // Rollback the transaction in case of error
			e.printStackTrace();
			return "An error occurred while updating the doctor: " + e.getMessage();
		} finally {
			session.close(); // Ensure the session is closed
		}
	}

	public Object deleteDoctor(Long id) {
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();

		// Fetch the Doctor using the provided id
		Doctor doctor = session.get(Doctor.class, id); // Use id directly, no quotes

		if (doctor != null) {
			session.delete(doctor); // Delete the doctor from the database

			// Close the session
			transaction.commit();

			return "Doctor Deleted Successfully";
		} else {
			session.close(); // Ensure session is closed even in case of no result
			return "Doctor with provided Id: " + id + " not found";
		}
	}

}
