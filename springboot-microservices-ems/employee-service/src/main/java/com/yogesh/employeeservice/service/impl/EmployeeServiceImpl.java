package com.yogesh.employeeservice.service.impl;

import com.yogesh.employeeservice.dto.APIResponseDto;
import com.yogesh.employeeservice.dto.EmployeeDto;
import com.yogesh.employeeservice.dto.OrganizationDto;
import com.yogesh.employeeservice.entity.Employee;
import com.yogesh.employeeservice.mapper.EmployeeMapper;
import com.yogesh.employeeservice.repository.EmployeeRepository;
import com.yogesh.employeeservice.service.DepartmentAPIClient;
import com.yogesh.employeeservice.service.EmployeeService;
import com.yogesh.employeeservice.service.OrganizationAPIClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import com.yogesh.employeeservice.dto.DepartmentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

//    private RestTemplate restTemplate;
//    private WebClient webClient;
    private DepartmentAPIClient departmentApiClient;
    private OrganizationAPIClient organizationAPIClient;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);

        Employee savedEmployee = employeeRepository.save(employee);

        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment") //circuit breaker pattern
//    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment") //retry pattern
    @Override
    public APIResponseDto getEmployeeById(Long employeeId) {
        log.info("getEmployeeById()");
        Employee employee = employeeRepository.findById(employeeId).get();

        //RestTemplate
/*        ResponseEntity<DepartmentDto> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/departments/" + employee.getDepartmentCode(),
                DepartmentDto.class);

        DepartmentDto departmentDto = responseEntity.getBody();
 */
        //WebClient
/*      DepartmentDto departmentDto = webClient.get()
                .uri("http://localhost:8080/api/departments/" + employee.getDepartmentCode())
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();
*/
        DepartmentDto departmentDto = departmentApiClient.getDepartment(employee.getDepartmentCode());
        OrganizationDto organizationDto = organizationAPIClient.getOrganizationByCode(employee.getOrganizationCode());

        EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDto(employee);

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployee(employeeDto);
        apiResponseDto.setDepartment(departmentDto);
        apiResponseDto.setOrganization(organizationDto);

        return apiResponseDto;
    }

    public APIResponseDto getDefaultDepartment(Long employeeId, Exception exception) {
        log.info("getDefaultDepartment()");

        Employee employee = employeeRepository.findById(employeeId).get();

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentName("R&D Department");
        departmentDto.setDepartmentCode("RD001");
        departmentDto.setDepartmentDescription("Research and Development Department");

        EmployeeDto employeeDto = EmployeeMapper.mapToEmployeeDto(employee);

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployee(employeeDto);
        apiResponseDto.setDepartment(departmentDto);
        return apiResponseDto;
    }
}
