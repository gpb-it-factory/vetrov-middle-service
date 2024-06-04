package ru.omon4412.minibank.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.omon4412.minibank.dto.UserDto;
import ru.omon4412.minibank.exception.ConflictException;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@ConditionalOnProperty(value = "application.registrationService.type", havingValue = "inMemory")
public class RegistrationServiceInMemoryImpl implements RegistrationService {
    private final Set<UserDto> users = ConcurrentHashMap.newKeySet();

    @Override
    public void registerUser(UserDto userDto) {
        if (users.contains(userDto)) {
            throw new ConflictException("Пользователь уже существует");
        }
        users.add(userDto);
    }
}
