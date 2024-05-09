package com.clearsolutionsltd.testtask.dto;

import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class UserDto {

    @Email(message = "must be in email address format")
    private String email;

    @NotEmpty(message = "This field can not be empty")
    private String firstName;

    @NotEmpty(message = "This field can not be empty")
    private String lastName;

    @Past
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDay;

    private String address;

    private String phoneNumber;

    public UserDto(String address, LocalDate birthDay, String email, String firstName, String lastName, String phoneNumber) {
        this.address = address;
        this.birthDay = birthDay;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
}
