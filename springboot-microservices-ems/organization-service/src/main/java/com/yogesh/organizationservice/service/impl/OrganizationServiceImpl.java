package com.yogesh.organizationservice.service.impl;

import com.yogesh.organizationservice.service.OrganizationService;
import lombok.AllArgsConstructor;
import com.yogesh.organizationservice.dto.OrganizationDto;
import com.yogesh.organizationservice.entity.Organization;
import com.yogesh.organizationservice.mapper.OrganizationMapper;
import com.yogesh.organizationservice.repository.OrganizationRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private OrganizationRepository organizationRepository;

    @Override
    public OrganizationDto saveOrganization(OrganizationDto organizationDto) {

        // convert OrganizationDto into Organization jpa entity
        Organization organization = OrganizationMapper.mapToOrganization(organizationDto);

        Organization savedOrganization = organizationRepository.save(organization);

        return OrganizationMapper.mapToOrganizationDto(savedOrganization);
    }

    @Override
    public OrganizationDto getOrganizationByCode(String organizationCode) {
        Organization organization = organizationRepository.findByOrganizationCode(organizationCode);
        return OrganizationMapper.mapToOrganizationDto(organization);
    }
}
