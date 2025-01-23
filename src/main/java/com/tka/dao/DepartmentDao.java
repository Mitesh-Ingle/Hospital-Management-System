package com.tka.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tka.entity.Department;

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
		Department department = session.get(Department.class,dId);

		if (department != null) {
			return department; // Return the team if found
		} else {
			return "department with provided ID not found"; // Return a message if not found
		}
	}

}