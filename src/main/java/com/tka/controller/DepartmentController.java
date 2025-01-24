package com.tka.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping("/get-by-id/{dId}")
	public Object getDepartmentById(@PathVariable("dId") Long dId) {
		return departmentService.getDepartmentById(dId);
	}

	@GetMapping("/get-by-name")
	public Object getDepartmentByName(@RequestParam String dName) {
		return departmentService.getDepartmentByName(dName);

	}

	@PutMapping("/update-department")
	public String updateDepartment(@RequestBody Department department) {
		return departmentService.updateDepartment(department);

	}


	@DeleteMapping("/delete-department/{dId}")
	public Object deleteDepartment(@PathVariable Long dId) {
	    return departmentService.deleteDepartment(dId);
	}


}