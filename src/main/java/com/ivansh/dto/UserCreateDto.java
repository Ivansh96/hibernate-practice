package com.ivansh.dto;

import com.ivansh.entity.PersonalInfo;

import javax.validation.Valid;
import java.util.UUID;

public record UserCreateDto(String username,
                            @Valid
                            PersonalInfo personalInfo,
                            UUID companyId
                            ) {
}
