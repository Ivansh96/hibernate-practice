package com.ivansh.mapper;

import com.ivansh.dto.UserReadDto;
import com.ivansh.entity.User;
import com.ivansh.exception.NoCompanyFieldException;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserReadMapper implements CustomMapper<User, UserReadDto> {

    private final CompanyReadMapper companyReadMapper;

    @Override
    public UserReadDto mapFrom(User object) {
        return new UserReadDto(
                object.getId(),
                object.getUsername(),
                object.getPersonalInfo(),
                Optional.ofNullable(object.getCompany())
                                .map(companyReadMapper::mapFrom)
                                        .orElseThrow(NoCompanyFieldException::new)

        );
    }
}
