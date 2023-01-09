package com.yogesh.springbootcrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yogesh.springbootcrud.model.Employee;

//no need to add @Repository and @Transactional, since JPARepository internally provides both the annotation
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	

}
