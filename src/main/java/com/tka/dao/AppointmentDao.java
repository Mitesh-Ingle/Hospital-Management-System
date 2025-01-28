package com.tka.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tka.entity.Appointment;
import com.tka.entity.Doctor;
import com.tka.entity.Patient;

@Repository
public class AppointmentDao {

	@Autowired
	SessionFactory sessionFactory;

	public String addAppointment(Appointment appointment) {
		System.err.println("In add appointment DAO");

		// Check if appointmentDate is null
		if (appointment.getAppointmentDate() == null) {
			return "Appointment Date cannot be empty";
		}
		System.err.println("Input Appointment Date: " + appointment.getAppointmentDate());

		try (Session session = sessionFactory.openSession()) {
			// Check if the doctor exists
			Doctor doctor = session.get(Doctor.class, appointment.getDoctor().getId());
			if (doctor == null) {
				return "No doctor found with ID: " + appointment.getDoctor().getId();
			}

			// Check if the patient exists
			Patient patient = session.get(Patient.class, appointment.getPatient().getpId());
			if (patient == null) {
				return "No patient found with ID: " + appointment.getPatient().getpId();
			}

			session.beginTransaction();

			// Debugging: Print the type and value of appointmentDate
			System.err.println("Appointment Date Type: " + appointment.getAppointmentDate().getClass().getName());
			System.err.println("Appointment Date Value: " + appointment.getAppointmentDate());

			// Check for existing appointment with the same date
			Criteria criteria = session.createCriteria(Appointment.class);
			criteria.add(Restrictions.eq("appointmentDate", appointment.getAppointmentDate()));
			Appointment existingAppo = (Appointment) criteria.uniqueResult();

			if (existingAppo != null) {
				// If appointment exists, return a message
				return "Appointment already present. Please provide a different date: "
						+ appointment.getAppointmentDate();
			} else {
				// If no existing appointment is found, save the new appointment
				session.saveOrUpdate(appointment);
				session.getTransaction().commit();
				return "Appointment Successfully Added";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed to add appointment due to an error: " + e.getMessage();
		}
	}

	public List<Appointment> getAllAppointment() {
		List<Appointment> appointmentsList = new ArrayList<>();
		try {
			Session session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Appointment.class);
			appointmentsList = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return appointmentsList;
	}

	public Object getAppointmentById(Long aId) {
		Session session = sessionFactory.openSession();
		Appointment appointment = session.get(Appointment.class, aId);
		if (appointment != null) {
			return appointment;
		} else {
			return "Appointment with provided ID: " + aId + " not found";
		}
	}

	public Object getAppointmentByDate(LocalDateTime appointmentDate) {
		System.err.println(1);
		Session session = sessionFactory.openSession();
		System.err.println(2);
		String hql = "FROM Appointment WHERE appointmentDate = : appointmentDate ";
		System.err.println(3);
		Query<Appointment> query = session.createQuery(hql, Appointment.class);
		System.err.println(4);
		query.setParameter("appointmentDate", appointmentDate);
		System.err.println(5);
		Appointment appointment = query.uniqueResult();
		System.err.println(6);
		if (appointment != null) {
			System.err.println(7);
			return appointment;
		} else {
			System.err.println(8);
			return "Appointment not available with provided date : " + appointmentDate;
		}
	}

	public String updateAppointment(Appointment appointment) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Appointment existingAppo = session.get(Appointment.class, appointment.getaId());
			if (existingAppo != null) {
				if (existingAppo.getAppointmentDate().equals(appointment.getAppointmentDate())) {
					return "No Changes made Please Add new Appointment Date";
				}
				existingAppo.setAppointmentDate(appointment.getAppointmentDate());
				session.update(existingAppo);
				transaction.commit();
				return "Appointment Updated successfully";
			} else {
				return "Appointment with ID : " + appointment.getaId() + " not exist please provide valid Id";
			}
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback(); // Rollback on failure
			throw e; // Re-throw the exception
		} finally {
			session.close(); // Ensure the session is closed
		}
	}

	public Object deleteAppointment(Long aId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		// Fetch the Appointment using the provided aId (not as a string)
		Appointment appointment = session.get(Appointment.class, aId); // Use aId directly, no quotes

		if (appointment != null) {
			session.delete(appointment);
			transaction.commit(); // Commit the transaction
			session.close();
			return "Appointment Deleted Successfully";
		} else {
			session.close(); // Ensure session is closed even in case of no result
			return "Appointment with provided Id: " + aId + " not found";
		}
	}

}