package com.clearsolutionsltd.testtask.conroller;

import com.clearsolutionsltd.testtask.dto.UserDto;
import com.clearsolutionsltd.testtask.exception.UserNotFoundException;
import com.clearsolutionsltd.testtask.service.UserService;
import com.clearsolutionsltd.testtask.user.UserEntity;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        userController = new UserController(userService);
    }

    @Test
    void testCreate_ValidUserDto_ReturnsOkResponse() {
        UserDto userDto = new UserDto(null, LocalDate.of(1990, 1, 1), "johndoe@gmail.com", "John", "Doe", null);
        UserEntity savedUser = new UserEntity(1L, "johndoe@gmail.com", "John", "Doe", LocalDate.of(1990, 1, 1), null, null);

        when(userService.save(userDto)).thenReturn(savedUser);

        ResponseEntity<UserEntity> response = userController.create(userDto);

        assertEquals(ResponseEntity.ok(savedUser), response);
        verify(userService, times(1)).save(userDto);
    }

    @Test
    void testCreate_NotValidUserDto_ReturnsOkResponse() {
        // Arrange
        UserDto userDto = new UserDto(null, LocalDate.now(), "johndoe@gmail.com", "John", "Doe", null);

        assertThrows(ValidationException.class, () -> userController.create(userDto));
    }

    @Test
    void testDelete_ExistUserId_ReturnsOkResponse() {
        long id = 1L;

        when(userService.delete(1L)).thenReturn(true);

        ResponseEntity<String> response = userController.delete(id);

        assertEquals(ResponseEntity.ok("User deleted"), response);
        verify(userService, times(1)).delete(id);
    }

    @Test
    void testDelete_UserNotFoundException_ReturnsBadRequestResponse() {
        long userId = 1L;

        when(userService.delete(userId)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userController.delete(userId));
        verify(userService, times(1)).delete(userId);
    }

    @Test
    void testPut_SuccessfulUpdate_ReturnsOkResponse() {
        long userId = 1L;
        UserDto userDto = new UserDto(null, LocalDate.of(1990, 1, 1), "johndoe@gmail.com", "John", "Doe", null);

        when(userService.update(userId, userDto, true)).thenReturn(true);

        ResponseEntity<String> response = userController.put(userId, userDto);

        assertEquals(ResponseEntity.ok("User updated"), response);
        verify(userService, times(1)).update(userId, userDto, true);
    }

    @Test
    void testPut_UnsuccessfulUpdate_ReturnsBadRequestResponse() {
        long userId = 1L;
        UserDto userDto = new UserDto(null, LocalDate.of(1990, 1, 1), "johndoe@gmail.com", "John", "Doe", null);

        when(userService.update(userId, userDto, true)).thenReturn(false);

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userController.put(userId, userDto));
        assertEquals("User not found", exception.getMessage());
        verify(userService, times(1)).update(userId, userDto, true);
    }

    @Test
    void testFindByRange_ReturnsUsersList() {
        // Arrange
        LocalDate from = LocalDate.of(1990, 1, 1);
        LocalDate to = LocalDate.of(1995, 12, 31);

        var user1 = new UserEntity(1L, "johndoe@gmail.com", "John", "Doe", LocalDate.of(1991, 1, 1), null, null);
        var user2 = new UserEntity(2L, "johndoe@gmail.com", "Jane", "Doe", LocalDate.of(1993, 1, 1), null, null);

        List<UserEntity> usersList = Arrays.asList(
                user1,
                user2

        );

        when(userService.findUsersByBirthDateRange(to, from)).thenReturn(usersList);

        ResponseEntity<List<UserEntity>> response = userController.findByRange(to, from);

        assertEquals(ResponseEntity.ok(usersList), response);
        verify(userService, times(1)).findUsersByBirthDateRange(to, from);
    }

    @Test
    void testFindByRange_EmptyList_ReturnsEmptyListResponse() {
        LocalDate from = LocalDate.of(1990, 1, 1);
        LocalDate to = LocalDate.of(1990, 12, 31);

        List<UserEntity> emptyList = List.of();
        when(userService.findUsersByBirthDateRange(to, from)).thenReturn(emptyList);

        ResponseEntity<List<UserEntity>> response = userController.findByRange(to, from);

        assertEquals(ResponseEntity.ok(emptyList), response);
        verify(userService, times(1)).findUsersByBirthDateRange(to, from);
    }
}