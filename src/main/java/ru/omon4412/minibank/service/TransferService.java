package ru.omon4412.minibank.service;

import ru.omon4412.minibank.dto.CreateTransferRequestDto;
import ru.omon4412.minibank.dto.TransferResponseDto;

public interface TransferService {

    TransferResponseDto transfer(CreateTransferRequestDto createTransferRequestDto);
}
