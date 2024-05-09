package com.clearsolutionsltd.testtask.conroller;

import com.clearsolutionsltd.testtask.dto.UserDto;
import com.clearsolutionsltd.testtask.exception.CustomValidationException;
import com.clearsolutionsltd.testtask.exception.UserNotFoundException;
import com.clearsolutionsltd.testtask.service.UserService;
import com.clearsolutionsltd.testtask.user.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private static final String NOT_FOUND = "User not found";

    @Value("${min.adult.age}")
    private int minAdultAge;

    @PostMapping
    public ResponseEntity<UserEntity> create(@RequestBody @Valid UserDto userDto) {
        if (!isAdult(userDto.getBirthDay())) {
            throw new CustomValidationException("User is not adult");
        }

        return ResponseEntity.ok(userService.save(userDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> put(@PathVariable long id, @RequestBody @Valid UserDto userDto) {
        var successful = userService.update(id, userDto, true);
        if (successful) {
            return ResponseEntity.ok("User updated");
        }
        throw new UserNotFoundException(NOT_FOUND);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> patch(@PathVariable long id, @RequestBody @Valid UserDto userDto) {
        var successful = userService.update(id, userDto, false);
        if (successful) {
            return ResponseEntity.ok("User updated");
        }
        throw new UserNotFoundException(NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        var successful = userService.delete(id);
        if (successful) {
            return ResponseEntity.ok("User deleted");
        }
        throw new UserNotFoundException(NOT_FOUND);
    }

    @GetMapping("{from}/{to}")
    public ResponseEntity<List<UserEntity>> findByRange(@PathVariable LocalDate to, @PathVariable LocalDate from) {
        var users = userService.findUsersByBirthDateRange(to, from);
        return ResponseEntity.ok(users);

    }

    private boolean isAdult(LocalDate birthDay) {
        return birthDay.plusYears(minAdultAge).isBefore(LocalDate.now());
    }

}
