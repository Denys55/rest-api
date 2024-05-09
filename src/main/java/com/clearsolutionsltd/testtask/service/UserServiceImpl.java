package com.clearsolutionsltd.testtask.service;

import com.clearsolutionsltd.testtask.dto.UserDto;
import com.clearsolutionsltd.testtask.repository.UserRepository;
import com.clearsolutionsltd.testtask.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity save(UserDto userDto) {
        return userRepository.save(mapper(userDto));
    }

    @Override
    public boolean update(long id, UserDto userDto, boolean allFields) {
        if (allFields) {
            return userRepository.update(mapperWithId(userDto, id));
        }
        var user = userRepository.findById(id);

        return user != null && userRepository.update(path(user, userDto));
    }

    @Override
    public boolean delete(long id) {
        return userRepository.delete(id);
    }

    @Override
    public List<UserEntity> findUsersByBirthDateRange(LocalDate from, LocalDate to) {
        return userRepository.findUsersByBirthDateRange(from, to);
    }

    private UserEntity mapper(UserDto userDto) {
        return UserEntity.builder()
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .birthDay(userDto.getBirthDay())
                .address(userDto.getAddress())
                .phoneNumber(userDto.getPhoneNumber())
                .build();
    }

    private UserEntity mapperWithId(UserDto userDto, long id) {
        var user = mapper(userDto);
        user.setId(id);

        return user;

    }

    private UserEntity path(UserEntity userEntity, UserDto userDto) {
        if (userDto.getEmail() != null && !userEntity.getEmail().equals(userDto.getEmail())) {
            userEntity.setEmail(userDto.getEmail());
        }
        if (userDto.getFirstName() != null && !userEntity.getFirstName().equals(userDto.getFirstName())) {
            userEntity.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null && !userEntity.getLastName().equals(userDto.getLastName())) {
            userEntity.setLastName(userDto.getLastName());
        }
        if (userDto.getBirthDay() != null && !userEntity.getBirthDay().equals(userDto.getBirthDay())) {
            userEntity.setBirthDay(userDto.getBirthDay());
        }
        if (userDto.getAddress() != null && !userEntity.getAddress().equals(userDto.getAddress())) {
            userEntity.setAddress(userDto.getAddress());
        }
        if (userDto.getPhoneNumber() != null && !userEntity.getPhoneNumber().equals(userDto.getPhoneNumber())) {
            userEntity.setPhoneNumber(userDto.getPhoneNumber());
        }

        return userEntity;
    }
}
