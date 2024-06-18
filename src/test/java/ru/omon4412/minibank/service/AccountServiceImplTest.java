package ru.omon4412.minibank.service;

import feign.RetryableException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ru.omon4412.minibank.client.BackendServiceClient;
import ru.omon4412.minibank.dto.NewAccountDto;
import ru.omon4412.minibank.exception.ConflictException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @Mock
    private BackendServiceClient backendServiceClient;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void test_createAccount_success() {
        NewAccountDto newAccountDto = new NewAccountDto();
        newAccountDto.setAccountName("test");
        when(backendServiceClient.createAccount(newAccountDto, 1L)).thenReturn(ResponseEntity.noContent().build());
        accountService.createAccount(1L, newAccountDto);
    }

    @Test
    void test_createAccount_whenAccountAlreadyExists() {
        NewAccountDto newAccountDto = new NewAccountDto();
        newAccountDto.setAccountName("test");
        ConflictException feignClientException = new ConflictException("");
        when(backendServiceClient.createAccount(newAccountDto, 1L)).thenThrow(feignClientException);
        assertThrows(ConflictException.class, () -> accountService.createAccount(1L, newAccountDto));
    }

    @Test
    void test_createAccount_whenServerIsDown() {
        NewAccountDto newAccountDto = new NewAccountDto();
        newAccountDto.setAccountName("test");
        when(backendServiceClient.createAccount(newAccountDto, 1L)).thenThrow(RetryableException.class);
        assertThrows(RetryableException.class, () -> accountService.createAccount(1L, newAccountDto));
    }
}