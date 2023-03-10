package com.yogesh.employeeservice.service;

import com.yogesh.employeeservice.dto.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("DEPARTMENT-SERVICE") //feign clients automatically does load balancing too
//feign client will dynamically create implementation for the methods
public interface DepartmentAPIClient {
    // Build get department rest api
    @GetMapping("api/departments/{department-code}")
    DepartmentDto getDepartment(@PathVariable("department-code") String departmentCode);
}
