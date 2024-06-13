package ru.omon4412.minibank.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import ru.omon4412.minibank.dto.UserDto;

@FeignClient(name = "backendService", url = "${application.backendService.url}")
public interface BackendServiceClient {

    @PostMapping("/v2/users")
    ResponseEntity<Void> registerUser(UserDto userRequestDto);
}
