package ru.omon4412.minibank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.omon4412.minibank.client.BackendServiceClient;
import ru.omon4412.minibank.dto.CreateTransferRequestDto;
import ru.omon4412.minibank.dto.TransferResponseDto;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "application.services.type", havingValue = "backend")
public class TransferServiceImpl implements TransferService {
    private final BackendServiceClient backendServiceClient;

    @Override
    public TransferResponseDto transfer(CreateTransferRequestDto createTransferRequestDto) {
        return backendServiceClient.transferMoney(createTransferRequestDto).getBody();
    }
}
