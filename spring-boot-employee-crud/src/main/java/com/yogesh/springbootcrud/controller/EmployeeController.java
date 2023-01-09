package com.yogesh.springbootcrud.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.yogesh.springbootcrud.exception.ResourceNotFoundException;
import com.yogesh.springbootcrud.model.Employee;
import com.yogesh.springbootcrud.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	private EmployeeService employeeService;

	//if there is a constructor with single field, there is no need of @Autowired annotation
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@PostMapping()
	public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
		return new ResponseEntity<Employee>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
	}
	
	@GetMapping() 
	public List<Employee> getAllEmployee(){
		return employeeService.getAllEmployee();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) throws ResourceNotFoundException{
		return new ResponseEntity<Employee>(employeeService.getEmployeeById(id), HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id, @RequestBody Employee employee) throws ResourceNotFoundException{
		return new ResponseEntity<Employee>(employeeService.updateEmployee(id, employee), HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long id) throws ResourceNotFoundException{
		employeeService.deleteEmployee(id);
		return new ResponseEntity<String>("Employee with id " + id + " deleted successfully", HttpStatus.OK);
	}
	
}















