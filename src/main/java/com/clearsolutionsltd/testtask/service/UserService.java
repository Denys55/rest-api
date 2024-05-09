package com.clearsolutionsltd.testtask.service;

import com.clearsolutionsltd.testtask.dto.UserDto;
import com.clearsolutionsltd.testtask.user.UserEntity;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public interface UserService {

    UserEntity save(UserDto userDto);

    boolean update(long id, UserDto userDto, boolean allFields);

    boolean delete(long id);

    List<UserEntity> findUsersByBirthDateRange(LocalDate from, LocalDate to);
}
