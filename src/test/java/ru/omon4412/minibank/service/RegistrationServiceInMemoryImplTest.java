package ru.omon4412.minibank.service;

import org.junit.jupiter.api.Test;
import ru.omon4412.minibank.dto.UserDto;
import ru.omon4412.minibank.exception.ConflictException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceInMemoryImplTest {

    private final RegistrationServiceInMemoryImpl registrationService = new RegistrationServiceInMemoryImpl();

    @Test
    void test_registerUser_success() {
        UserDto userDto = new UserDto();
        userDto.setUserId(101010L);
        registrationService.registerUser(userDto);
    }

    @Test
    void test_registerUser_whenUserAlreadyRegistered() {
        UserDto userDto = new UserDto();
        userDto.setUserId(101010L);
        registrationService.registerUser(userDto);
        assertThrows(ConflictException.class, () -> registrationService.registerUser(userDto));
    }
}