package com.ivansh.mapper;

import com.ivansh.dao.CompanyRepository;
import com.ivansh.dto.UserCreateDto;
import com.ivansh.entity.User;
import com.ivansh.exception.NoCompanyException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCreateMapper implements CustomMapper<UserCreateDto, User> {

    private final CompanyRepository companyRepository;

    @Override
    public User mapFrom(UserCreateDto object) {
        return User.builder()
                .personalInfo(object.personalInfo())
                .username(object.username())
                .company(companyRepository.findById(object.companyId())
                        .orElseThrow(NoCompanyException::new))
                .build();
    }
}
