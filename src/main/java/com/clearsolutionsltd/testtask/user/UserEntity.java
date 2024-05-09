package com.clearsolutionsltd.testtask.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private long id;

    private String email;

    private String firstName;

    private String lastName;

    private LocalDate birthDay;

    private String address;

    private String phoneNumber;

    public UserEntity(String address, LocalDate birthDay, String email, String firstName, long id, String lastName, String phoneNumber) {
        this.address = address;
        this.birthDay = birthDay;
        this.email = email;
        this.firstName = firstName;
        this.id = id;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
}
