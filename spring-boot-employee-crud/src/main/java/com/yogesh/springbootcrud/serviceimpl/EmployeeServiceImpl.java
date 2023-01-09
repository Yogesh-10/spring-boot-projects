package com.yogesh.springbootcrud.serviceimpl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.yogesh.springbootcrud.exception.ResourceNotFoundException;
import com.yogesh.springbootcrud.model.Employee;
import com.yogesh.springbootcrud.repository.EmployeeRepository;
import com.yogesh.springbootcrud.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	private EmployeeRepository employeeRepository;
	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public List<Employee> getAllEmployee() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(Long id) throws ResourceNotFoundException {
		Optional<Employee> employee = employeeRepository.findById(id);
		if (!employee.isPresent()) 
			throw new ResourceNotFoundException("Employee Not Found" + id);
		
		return employee.get();
//		return employeeRepository.findById(id).
//				orElseThrow(() -> new ResourceNotFoundException("Employee", "ID", id));
	}

	@Override
	public Employee updateEmployee(Long id, Employee employee) throws ResourceNotFoundException {
		Optional<Employee> employee1 = employeeRepository.findById(id);
		if (!employee1.isPresent()) 
			throw new ResourceNotFoundException("Employee Not Found" + id);
		
		Employee existingEmployee = employee1.get();
		
		//check if the property is notNull or not empty. If it is NotNull and NotEmpty, then only update
        if(Objects.nonNull(employee.getFirstName()) &&
        !"".equalsIgnoreCase(employee.getFirstName())) {
            existingEmployee.setFirstName(employee.getFirstName());
        }

        if(Objects.nonNull(employee.getLastName()) &&
                !"".equalsIgnoreCase(employee.getLastName())) {
            existingEmployee.setLastName(employee.getLastName());
        }

        if(Objects.nonNull(employee.getEmail()) &&
                !"".equalsIgnoreCase(employee.getEmail())) {
            existingEmployee.setEmail(employee.getEmail());
        }

        return employeeRepository.save(existingEmployee);
	}

	@Override
	public void deleteEmployee(Long id) throws ResourceNotFoundException {
		Optional<Employee> employee = employeeRepository.findById(id);
		if (!employee.isPresent()) 
			throw new ResourceNotFoundException("Employee Not Found" + id);

		employeeRepository.deleteById(id);		
	}
	
}
