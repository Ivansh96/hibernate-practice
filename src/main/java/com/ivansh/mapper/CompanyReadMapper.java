package com.ivansh.mapper;

import com.ivansh.dto.CompanyReadDto;
import com.ivansh.entity.Company;
import org.hibernate.Hibernate;

public class CompanyReadMapper implements CustomMapper<Company, CompanyReadDto> {

    @Override
    public CompanyReadDto mapFrom(Company object) {
        return new CompanyReadDto(
                object.getId(),
                object.getName(),
                object.getLocales()
        );
    }
}
