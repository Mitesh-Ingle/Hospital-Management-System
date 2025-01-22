package com.tka.dao;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tka.entity.Patient;

@Repository
public class PatientDao {

	@Autowired
	SessionFactory sessionFactory;

	public String addPatient(Patient patient) {
		System.err.println("In Add Patient Dao");

		if (patient.getpName() == null || patient.getpName().isEmpty()) {
			return "Patient name cannot be empty";
		}
		if (patient.getpDisease() == null || patient.getpDisease().isEmpty()) {
			return "Patient disease cannot be empty";
		}
		if (patient.getpContact() == null || patient.getpContact().isEmpty()) {
			return "Patient contact number cannot be empty";
		}
		if (patient.getpEmail() == null || patient.getpEmail().isEmpty()) {
			return "Patient email cannot be empty";
		}
		if (patient.getpAddress() == null || patient.getpAddress().isEmpty()) {
			return "Patient address cannot be empty";
		}
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		System.err.println(session);
		// Check if patients exist with the same email
		Criteria emailCriteria = session.createCriteria(Patient.class);
		emailCriteria.add(Restrictions.eq("pEmail", patient.getpEmail()));
		List<Patient> emailMatches = emailCriteria.list();
		System.err.println(1);

		// Check if patients exist with the same contact
		Criteria contactCriteria = session.createCriteria(Patient.class);
		contactCriteria.add(Restrictions.eq("pContact", patient.getpContact()));
		List<Patient> contactMatches = contactCriteria.list();
		System.err.println(2);

		// Validate email and contact independently
		if (!emailMatches.isEmpty() && !contactMatches.isEmpty()) {
			session.close();
			System.err.println(3);

			return "Both email and contact already exist. Please provide different email and contact.";

		} else if (!emailMatches.isEmpty()) {
			session.close();
			System.err.println(4);

			return "The email " + patient.getpEmail() + " already exists. Please provide a different email.";
		} else if (!contactMatches.isEmpty()) {
			session.close();
			System.err.println(5);

			return "The contact " + patient.getpContact() + " already exists. Please provide a different contact.";
		}

		// Save or update the patient if both email and contact are unique
		session.saveOrUpdate(patient);
		System.err.println(6);

		session.getTransaction().commit();
		System.err.println(7);

		session.close();
		System.err.println(8);

		return "Patient added successfully.";
	}

	public List<Patient> getPatient() {
		List<Patient> patientsList = new ArrayList<>();
		System.err.println("In get Patient Dao");
		try {
			Session session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Patient.class);
			patientsList = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return patientsList;
	}
}
