package ru.omon4412.minibank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.omon4412.minibank.client.BackendServiceClient;
import ru.omon4412.minibank.dto.UserDto;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "application.registrationService.type", havingValue = "backend")
public class RegistrationServiceImpl implements RegistrationService {
    private final BackendServiceClient backendServiceClient;

    @Override
    public void registerUser(UserDto userDto) {
        backendServiceClient.registerUser(userDto);
    }
}
