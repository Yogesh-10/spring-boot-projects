package com.yogesh.springbootcrud.service;

import java.util.List;
import java.util.Optional;

import com.yogesh.springbootcrud.exception.ResourceNotFoundException;
import com.yogesh.springbootcrud.model.Employee;

public interface EmployeeService {
	Employee saveEmployee(Employee employee);
	List<Employee> getAllEmployee();
	Employee getEmployeeById(Long id) throws ResourceNotFoundException;
	Employee updateEmployee(Long id, Employee employee) throws ResourceNotFoundException;
	void deleteEmployee(Long id) throws ResourceNotFoundException;
}
