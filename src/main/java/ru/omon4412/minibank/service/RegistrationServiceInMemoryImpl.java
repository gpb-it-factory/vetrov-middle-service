package ru.omon4412.minibank.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.omon4412.minibank.dto.UserRequestDto;
import ru.omon4412.minibank.dto.UsernameResponseDto;
import ru.omon4412.minibank.exception.ConflictException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@ConditionalOnProperty(value = "application.registrationService.type", havingValue = "inMemory")
public class RegistrationServiceInMemoryImpl implements RegistrationService {
    private final Map<Long, UserRequestDto> users = new ConcurrentHashMap<>();

    @Override
    public void registerUser(UserRequestDto userRequestDto) {
        if (users.containsKey(userRequestDto.getUserId())) {
            throw new ConflictException("Пользователь уже зарегистрирован");
        }
        users.put(userRequestDto.getUserId(), userRequestDto);
    }

    @Override
    public UsernameResponseDto getUsernameById(Long id) {
        UserRequestDto userRequestDto = users.get(id);
        if (userRequestDto == null) {
            return null;
        }
        return new UsernameResponseDto(userRequestDto.getUserName());
    }
}
