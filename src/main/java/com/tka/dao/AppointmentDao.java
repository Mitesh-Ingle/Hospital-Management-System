package com.tka.dao;

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
		System.err.println("In add appointment dao");
		System.err.println(1);
		try {
			System.err.println(2);
			Session session = sessionFactory.openSession();
			System.err.println(3);
			session.beginTransaction();
			System.err.println(4);
			Criteria criteria = session.createCriteria(Appointment.class);
			System.err.println(5);
			criteria.add(Restrictions.eq("appointmentDate", appointment.getAppointmentDate()));
			System.err.println(6);
			Appointment existingAppo = (Appointment) criteria.uniqueResult();
			System.err.println(7);
			if (existingAppo != null) {
				System.err.println(8);
				return " Appointment already present please provide different date : "
						+ appointment.getAppointmentDate();

			} else {
				System.err.println(9);
				session.saveOrUpdate(appointment);
				System.err.println(10);
				session.getTransaction().commit();
			}
		} catch (Exception e) {
			System.err.println(11);
			e.printStackTrace();
			System.err.println(12);
		}
		return "Appointment Successfully Added";
	}
}
