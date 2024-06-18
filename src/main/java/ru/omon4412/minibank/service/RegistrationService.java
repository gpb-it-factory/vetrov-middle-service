package ru.omon4412.minibank.service;

import ru.omon4412.minibank.dto.UserRequestDto;
import ru.omon4412.minibank.dto.UsernameResponseDto;

public interface RegistrationService {

    void registerUser(UserRequestDto userRequestDto);

    UsernameResponseDto getUsernameById(Long id);
}
