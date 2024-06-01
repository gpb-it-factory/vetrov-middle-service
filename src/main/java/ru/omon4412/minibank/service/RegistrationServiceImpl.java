package ru.omon4412.minibank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.omon4412.minibank.client.BackendServiceClient;
import ru.omon4412.minibank.dto.UserDto;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final BackendServiceClient backendServiceClient;

    @Override
    public void registerUser(UserDto userDto) {
        backendServiceClient.registerUser(userDto);
    }
}
