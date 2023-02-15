package com.yogesh.employeeservice.service;

import com.yogesh.employeeservice.dto.APIResponseDto;
import com.yogesh.employeeservice.dto.EmployeeDto;
public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto);

    APIResponseDto getEmployeeById(Long employeeId);
}
