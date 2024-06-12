package ru.omon4412.minibank.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.omon4412.minibank.dto.NewAccountDto;
import ru.omon4412.minibank.dto.UserRequestDto;
import ru.omon4412.minibank.dto.UsernameResponseDto;

@FeignClient(name = "backendService", url = "${application.backendService.url}")
public interface BackendServiceClient {

    @PostMapping("/v2/users")
    ResponseEntity<Void> registerUser(UserRequestDto userRequestDto);

    @PostMapping("/v2/users/{id}/accounts")
    ResponseEntity<Void> createAccount(NewAccountDto newAccountDto, @PathVariable("id") Long userId);

    @GetMapping("/v2/users/{id}/username")
    ResponseEntity<UsernameResponseDto> getUsernameById(@PathVariable("id") Long userId);
}
