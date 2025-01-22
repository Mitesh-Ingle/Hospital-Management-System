package com.tka.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tka.entity.Department;
import com.tka.service.DepartmentService;

@RestController
@RequestMapping("/department")
public class DepartmentController {

	@Autowired
	DepartmentService departmentService;

	@PostMapping("/add-department")
	public String addDepartment(@RequestBody Department department) {
		System.err.println("In Department Controller");
		System.err.println("Received Department: " + department);
		return departmentService.addDepartment(department);

	}

	@GetMapping("/get-all-departments")
	public List<Department> getAllDepartments() {
	    System.err.println("Inside getAllDepartments Controller");
		return departmentService.getAllDepartments();

	}
}