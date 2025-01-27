package com.tka.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tka.entity.Department;
import com.tka.entity.Doctor;
import com.tka.entity.Patient;

@Repository
public class DepartmentDao {

	@Autowired
	SessionFactory sessionFactory;

	public String addDepartment(Department department) {
		System.err.println("In department Dao");
		System.err.println(department);

		// Use try-with-resources to ensure session is closed even if an exception
		// occurs
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();

			// Check if a department with the same name already exists using Criteria API
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Department> cq = cb.createQuery(Department.class);
			Root<Department> root = cq.from(Department.class);
			cq.select(root).where(cb.equal(root.get("dName"), department.getdName()));
			List<Department> existingDepartments = session.createQuery(cq).getResultList();

			// If a department with the same name exists, return a message
			if (!existingDepartments.isEmpty()) {
				session.getTransaction().commit();
				return "Department with Name: " + department.getdName() + " already exists.";
			}

			// If no existing department, save the new department
			session.saveOrUpdate(department);
			session.getTransaction().commit();

			return "Department added successfully.";
		} catch (Exception e) {
			// Log the exception and handle the rollback if needed
			e.printStackTrace();
			return "Error adding department: " + e.getMessage();
		}
	}

	public List<Department> getAllDepartments() {
		System.err.println("In Get All Department Dao");
		List<Department> departmentsList = new ArrayList<>();
		try {
			Session session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Department.class);
			departmentsList = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return departmentsList;

	}

	public Object getDepartmentById(Long dId) {
		Session session = sessionFactory.openSession();
		Department department = session.get(Department.class, dId);

		if (department != null) {
			return department; // Return the department if found
		} else {
			return "department with provided ID: " + dId + " not found"; // Return a message if not found
		}
	}

	public Object getDepartmentByName(String dName) {
		Session session = sessionFactory.openSession();
		String hql = "FROM Department WHERE dName = :dName"; // Fixed space issue
		Query<Department> query = session.createQuery(hql, Department.class);
		query.setParameter("dName", dName);

		List<Department> departments = query.list(); // Fetch all matching departments

		if (!departments.isEmpty()) {
			return departments; // Return the list of departments
		} else {
			return "No department available with name: " + dName;
		}
	}

	public String updateDepartment(Department department) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Department existingDepartment = session.get(Department.class, department.getdId());
		if (existingDepartment != null) {
			// Check if the details are the same
			if (existingDepartment.getdName().equals(department.getdName())
					&& existingDepartment.getdDescription().equals(department.getdDescription())) {
				return "No changes detected. The department name and description are the same.";
			}

			// Update the department details
			existingDepartment.setdName(department.getdName());
			existingDepartment.setdDescription(department.getdDescription());

			// Save the updated department
			session.update(existingDepartment);

			// Commit the transaction
			transaction.commit();
			return "Department updated successfully";
		} else {
			return "Department with ID " + department.getdId() + " does not exist. Please provide a valid ID.";
		}
	}

	public Object deleteDepartment(Long dId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		System.err.println(1);
		try {
			System.err.println(2);
			// Fetch the department from the database using the provided dId
			Department department = session.get(Department.class, dId);
			System.err.println(3);

			if (department != null) {
				System.err.println(4);
				// Manually delete associated patients
				if (department.getPatients() != null && !department.getPatients().isEmpty()) {
					for (Patient patient : department.getPatients()) {
						patient.setDepartment(null); // Break relationship with department
						session.delete(patient); // Delete patient manually
					}
				}

				// Manually delete associated doctors
				if (department.getDoctors() != null && !department.getDoctors().isEmpty()) {
					for (Doctor doctor : department.getDoctors()) {
						doctor.setDepartment(null); // Break relationship with department
						session.delete(doctor); // Delete doctor manually
					}
				}

				// Now delete the department
				session.delete(department);
				transaction.commit();
				System.err.println(5);

				System.err.println(6);
				return "Department records deleted successfully.";
			} else {
				System.err.println(7);
				return "There is no department with ID: " + dId;
			}

		} catch (Exception e) {
			System.err.println(8);
			if (transaction != null) {
				System.err.println(9);
				transaction.rollback();
				System.err.println(10); // Rollback in case of an error
			}

			return "Error deleting department: " + e.getMessage();
		} finally {
			System.err.println(11);
			session.close(); // Always close the session
			System.err.println(12);
		}
	}

}
