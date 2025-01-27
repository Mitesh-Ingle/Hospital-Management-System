package com.tka.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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

		// Check if patients exist with the same contact
		Criteria contactCriteria = session.createCriteria(Patient.class);
		contactCriteria.add(Restrictions.eq("pContact", patient.getpContact()));
		List<Patient> contactMatches = contactCriteria.list();

		// Validate email and contact independently
		if (!emailMatches.isEmpty() && !contactMatches.isEmpty()) {
			session.close();
			return "Email and contact already exist. Please provide a different email and contact.";
		} else if (!emailMatches.isEmpty()) {
			session.close();
			return "The email " + patient.getpEmail() + " already exists. Please provide a different email.";
		} else if (!contactMatches.isEmpty()) {
			session.close();
			return "The contact number " + patient.getpContact()
					+ " already exists. Please provide a different contact.";
		}

		// Save or update the patient if both email and contact are unique
		session.saveOrUpdate(patient);
		session.getTransaction().commit();
		session.close();

		return "Patient added successfully";
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

	public Object getPatientById(Long pId) {
		Session session = sessionFactory.openSession();
		Patient patient = session.get(Patient.class, pId);
		if (patient != null) {
			return patient;
		} else {
			return "Patient not found with the provided ID: " + pId;
		}
	}

	public Object getPatientByName(String pName) {
		Session session = sessionFactory.openSession();
		String hql = "FROM Patient WHERE pName = :pName";
		Query<Patient> query = session.createQuery(hql, Patient.class);
		query.setParameter("pName", pName);

		List<Patient> patients = query.list(); // Fetch all patients with the given name

		if (!patients.isEmpty()) {
			return patients; // Return the list of patients
		} else {
			return "No patients found with the provided name: " + pName;
		}
	}

	public String updatePatient(Patient patient) {

		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		try {
			Patient existingPatinet = session.get(Patient.class, patient.getpId());
			if (existingPatinet != null) {
				if (existingPatinet.getpName().equals(patient.getpName())
						&& existingPatinet.getpDisease().equals(patient.getpDisease())
						&& existingPatinet.getpContact().equals(patient.getpContact())
						&& existingPatinet.getpEmail().equals(patient.getpEmail())) {
					return "No changes were made as the Patient's: Name, Disease, Contact, and Email remain the same.";
				}
				existingPatinet.setpName(patient.getpName());
				existingPatinet.setpDisease(patient.getpDisease());
				existingPatinet.setpContact(patient.getpContact());
				existingPatinet.setpAddress(patient.getpAddress());
				existingPatinet.setpEmail(patient.getpEmail());

				session.update(existingPatinet);
				transaction.commit();

				return "Patient Updated Successfully";

			} else {
				return "Patient with provided ID :" + patient.getpId() + "not present ENTER VALID ID";

			}

		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
			return "Error occur while updating patient " + e.getMessage();
		} finally {
			session.close();
		}

	}

	public Object deletePatient(Long pId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		try {
			// Fetch the Patient by ID
			Patient patient = session.get(Patient.class, pId);

			if (patient != null) {
				// Break the relationship with Department
				if (patient.getDepartment() != null) {
					patient.getDepartment().getPatients().remove(patient);
					patient.setDepartment(null);
				}

				// Delete the Patient
				session.delete(patient);
				transaction.commit();
				return "Patient deleted Successfully";
			} else {
				return "Patient not found with ID: " + pId;
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			return "Error deleting Patient: " + e.getMessage();
		} finally {
			session.close();
		}
	}

}
