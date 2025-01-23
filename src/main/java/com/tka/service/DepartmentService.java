package com.tka.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.tka.dao.DepartmentDao;
import com.tka.entity.Department;

@Service
public class DepartmentService {
	@Autowired
	DepartmentDao departmentDao;

	public String addDepartment(@RequestBody Department department) {
		System.err.println("In Department Service");
		return departmentDao.addDepartment(department);
	}
	
	 public List<Department> getAllDepartments() {
		    System.err.println("Inside getAllDepartments service");
	        return departmentDao.getAllDepartments();
	    }
	 
	public Object getDepartmentById(Long dId) {
		return (Department) departmentDao.getDepartmentById(dId);
	}
}
