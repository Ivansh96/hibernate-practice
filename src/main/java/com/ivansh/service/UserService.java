package com.ivansh.service;

import com.ivansh.dao.UserRepository;
import com.ivansh.dto.UserCreateDto;
import com.ivansh.dto.UserReadDto;
import com.ivansh.exception.NoSuchUserException;
import com.ivansh.mapper.UserCreateMapper;
import com.ivansh.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.UUID;

@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateMapper userCreateMapper;

    @Transactional
    public boolean isDelete(UUID id) {
        var maybeUser = userRepository.findById(id);
        maybeUser.ifPresent(user -> userRepository.delete(id));

        return maybeUser.isPresent();
    }

    @Transactional
    public UserReadDto findById(UUID id) {
        return userRepository.findById(id)
                .map(userReadMapper::mapFrom)
                .orElseThrow(NoSuchUserException::new);
    }

    @Transactional
    public UUID create(UserCreateDto userCreateDto) {
        var userEntity = userCreateMapper.mapFrom(userCreateDto);

        return userRepository.save(userEntity).getId();
    }
}
