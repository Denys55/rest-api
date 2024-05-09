package com.clearsolutionsltd.testtask.repository;

import com.clearsolutionsltd.testtask.user.UserEntity;


import java.time.LocalDate;
import java.util.List;

public interface UserRepository {
    UserEntity save(UserEntity userEntity);

    boolean update(UserEntity userEntity);

    boolean delete(long id);

    List<UserEntity> findUsersByBirthDateRange(LocalDate from, LocalDate to);

    UserEntity findById(long id);
}
