package ru.omon4412.minibank.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.omon4412.minibank.dto.UserDto;
import ru.omon4412.minibank.exception.ConflictException;

import java.util.HashSet;
import java.util.Set;

@Service
@Primary
public class RegistrationServiceInMemoryImpl implements RegistrationService {
    private final Set<UserDto> users = new HashSet<>();

    @Override
    public void registerUser(UserDto userDto) {
        if (users.contains(userDto)) {
            throw new ConflictException("Пользователь уже существует");
        }
        users.add(userDto);
    }
}
