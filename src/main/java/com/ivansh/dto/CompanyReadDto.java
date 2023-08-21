package com.ivansh.dto;

import com.ivansh.entity.LocaleInfo;

import java.util.List;
import java.util.UUID;

public record CompanyReadDto(UUID id,
                             String name,
                             List<LocaleInfo> locales) {
}
