package ru.omon4412.minibank.service;

import feign.RetryableException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.omon4412.minibank.client.BackendServiceClient;
import ru.omon4412.minibank.dto.UserDto;
import ru.omon4412.minibank.exception.ConflictException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceImplTest {
    @Mock
    private BackendServiceClient backendServiceClient;

    @InjectMocks
    private RegistrationServiceImpl registrationService;


    @Test
    void test_registerUser_success() {
        UserDto userDto = new UserDto();
        ResponseEntity<Void> responseEntity = ResponseEntity.noContent().build();
        when(backendServiceClient.registerUser(any(UserDto.class))).thenReturn(responseEntity);

        registrationService.registerUser(userDto);

        verify(backendServiceClient, times(1)).registerUser(any(UserDto.class));
    }

    @Test
    void test_registerUser_whenUserAlreadyRegistered() {
        ConflictException feignClientException = new ConflictException("");
        when(backendServiceClient.registerUser(any(UserDto.class)))
                .thenThrow(feignClientException);
        assertThrows(ConflictException.class, () -> registrationService.registerUser(new UserDto()));
        verify(backendServiceClient, times(1)).registerUser(any(UserDto.class));
    }


    @Test
    void test_registerUser_whenServerIsDown() {
        UserDto userRequestDto = new UserDto();
        when(backendServiceClient.registerUser(any(UserDto.class))).thenThrow(RetryableException.class);

        assertThrows(RetryableException.class, () -> registrationService.registerUser(userRequestDto));

        verify(backendServiceClient, times(1)).registerUser(any(UserDto.class));
    }
}