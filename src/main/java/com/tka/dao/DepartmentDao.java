package com.tka.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		// Fetch all departments and check if any has the same name
		List<Department> departments = session.createCriteria(Department.class).list();
		for (Department existingDepartment : departments) {
			// Use Objects.equals() to safely handle null values
			if (Objects.equals(existingDepartment.getdName(), department.getdName())) {
				session.getTransaction().commit();
				session.close();
				return "Department with Name : " + department.getdName() + " already exists.";
			}
		}
		// If no existing Department, save the new Department
		session.saveOrUpdate(department);
		session.getTransaction().commit();
		session.close();
		return "Department added successfully.";
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
		String hql = "FROM Department WHERE dName = : dName";
		Query<Department> query = session.createQuery(hql, Department.class);
		query.setParameter("dName", dName);
		Department department = query.uniqueResult();
		if (department != null) {
			return department;
		} else {
			return "No department available with : " + dName + "provided";
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
				session.delete(department);
				System.err.println(5);

				System.err.println(6);
				return "Department and associated records deleted successfully.";
			} else {
				System.err.println(7);
				return "Department not found with Id : " + dId;

			}

		} catch (Exception e) {
			System.err.println(8);
			if (transaction != null) {
				System.err.println(9);
				transaction.rollback();
				System.err.println(10);// Rollback in case of an error
			}

			return "Error deleting department: " + e.getMessage();
		} finally {
			System.err.println(11);
			session.close(); // Always close the session
			System.err.println(12);
		}
	}

}
