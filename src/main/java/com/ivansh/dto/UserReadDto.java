package com.ivansh.dto;

import com.ivansh.entity.Company;
import com.ivansh.entity.PersonalInfo;

import java.util.UUID;

public record UserReadDto(UUID id,
                          String username,
                          PersonalInfo personalInfo,
                          CompanyReadDto company) {
}
