package com.tka.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tka.entity.Appointment;

@Repository
public class AppointmentDao {

	@Autowired
	SessionFactory sessionFactory;

	public String addAppointment(Appointment appointment) {
		System.err.println("In add appointment DAO");
		System.err.println(1);
		// Check if appointmentDate is null
		if (appointment.getAppointmentDate() == null) {
			System.err.println(2);
			return "Appointment Date cannot be empty";
		}
		System.err.println("Input Appointment Date: " + appointment.getAppointmentDate());
		try (Session session = sessionFactory.openSession()) {
			System.err.println(3);
			session.beginTransaction();
			System.err.println(4);

			// Debugging: Print the type and value of appointmentDate
			System.err.println("Appointment Date Type: " + appointment.getAppointmentDate().getClass().getName());
			System.err.println(5);
			System.err.println("Appointment Date Value: " + appointment.getAppointmentDate());
			System.err.println(6);
			// Check for existing appointment with the same date
			Criteria criteria = session.createCriteria(Appointment.class);
			System.err.println(7);
			criteria.add(Restrictions.eq("appointmentDate", appointment.getAppointmentDate()));
			System.err.println(8);
			Appointment existingAppo = (Appointment) criteria.uniqueResult();
			System.err.println(9);
			System.err.println("Existing Appointment: " + existingAppo);
			System.err.println(10);
			if (existingAppo != null) {
				System.err.println(11);
				// If appointment exists, return a message
				return "Appointment already present. Please provide a different date: "
						+ appointment.getAppointmentDate();
			} else {
				System.err.println(12);
				// If no existing appointment is found, save the new appointment
				session.saveOrUpdate(appointment);
				System.err.println(13);
				session.getTransaction().commit();
				System.err.println(14);
				return "Appointment Successfully Added";
			}
		} catch (Exception e) {
			System.err.println(15);
			e.printStackTrace();
			System.err.println(16);
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
			return "Appointment with provided ID not found";
		}
	}

}