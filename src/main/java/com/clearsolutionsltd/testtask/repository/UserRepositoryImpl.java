package com.clearsolutionsltd.testtask.repository;

import com.clearsolutionsltd.testtask.user.UserEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Repository
public class UserRepositoryImpl implements UserRepository {

    //the better solution for find by range this is TreeSet,
    // but I see more methods now need find by id, so I prefer use hashMap with constant time find user
    private final Map<Long, UserEntity> users = new HashMap<>();
    private final Random random = new Random();



    @Override
    public boolean delete(long id) {
        var user = users.get(id);
        if (user != null) {
            users.remove(id);
            return true;
        }

        return false;
    }


    @Override
    public UserEntity save(UserEntity userEntity) {
        var id = generateId();
        userEntity.setId(id);
        users.put(id, userEntity);
        return userEntity;
    }

    @Override
    public boolean update(UserEntity userEntity) {
        var user = users.get(userEntity.getId());
        if (user != null) {
            users.put(user.getId(), userEntity);
            return true;
        }

        return false;
    }

    @Override
    public List<UserEntity> findUsersByBirthDateRange(LocalDate from, LocalDate to) {
        return users.values().stream()
                .filter(user -> user.getBirthDay().isAfter(from) && user.getBirthDay().isBefore(to))
                .toList();
    }

    @Override
    public UserEntity findById(long id) {
        return users.get(id);
    }

    private Long generateId() {
        long timestamp = System.currentTimeMillis();
        long randomLong = random.nextLong();

        return timestamp + randomLong;
    }


}
