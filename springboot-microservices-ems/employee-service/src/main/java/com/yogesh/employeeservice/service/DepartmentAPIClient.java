package com.yogesh.employeeservice.service;

import com.yogesh.employeeservice.dto.DepartmentDto;
import com.yogesh.employeeservice.dto.OrganizationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("DEPARTMENT-SERVICE") //feign clients automatically does load balancing too
//feign client will dynamically create implementation for the methods
public interface APIClient {
    // Build get department rest api
    @GetMapping("api/departments/{department-code}")
    DepartmentDto getDepartmentByCode(@PathVariable("department-code") String departmentCode);

    @GetMapping("/api/organizations/{organization-code}")
    OrganizationDto getOrganizationByCode(@PathVariable("organization-code")  String organizationCode);
}
