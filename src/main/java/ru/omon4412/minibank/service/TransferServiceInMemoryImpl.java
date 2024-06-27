package ru.omon4412.minibank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.omon4412.minibank.dto.CreateTransferRequestDto;
import ru.omon4412.minibank.dto.ResponseAccountDto;
import ru.omon4412.minibank.dto.TransferResponseDto;
import ru.omon4412.minibank.dto.UserIdResponseDto;
import ru.omon4412.minibank.exception.ConflictException;
import ru.omon4412.minibank.exception.NotFoundException;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "application.services.type", havingValue = "inMemory")
public class TransferServiceInMemoryImpl implements TransferService {
    private final Map<String, CreateTransferRequestDto> transferResponseDtoMap = new ConcurrentHashMap<>();
    private final RegistrationService registrationService;
    private final AccountServiceBalanceUpdate accountService;

    @Override
    public TransferResponseDto transfer(CreateTransferRequestDto createTransferRequestDto) {
        if (registrationService.getUserIdByUsername(createTransferRequestDto.getFrom()) == null) {
            throw new NotFoundException("Сначала нужно зарегистрироваться");
        }
        UserIdResponseDto fromUserId = registrationService.getUserIdByUsername(createTransferRequestDto.getFrom());
        ResponseAccountDto accountFrom = accountService.getUserAccounts(fromUserId.getUserId())
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("У вас нет счетов"));
        if (registrationService.getUserIdByUsername(createTransferRequestDto.getTo()) == null) {
            throw new NotFoundException("Конечный пользователь не найден");
        }
        if (createTransferRequestDto.getFrom().equals(createTransferRequestDto.getTo())) {
            throw new ConflictException("Нельзя перевести средства самому себе");
        }
        if (createTransferRequestDto.getAmount() <= 0) {
            throw new ConflictException("Нельзя перевести отрицательное количество средств");
        }

        UserIdResponseDto toUserId = registrationService.getUserIdByUsername(createTransferRequestDto.getTo());

        ResponseAccountDto accountTo = accountService.getUserAccounts(toUserId.getUserId())
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("У конечного пользователя нет счетов"));

        if (accountFrom.getAmount() < createTransferRequestDto.getAmount()) {
            throw new ConflictException("Недостаточно средств");
        }
        accountFrom.setAmount(accountFrom.getAmount() - createTransferRequestDto.getAmount());
        accountTo.setAmount(accountTo.getAmount() + createTransferRequestDto.getAmount());

        accountService.updateAccount(accountFrom);
        accountService.updateAccount(accountTo);

        String transferUUID = UUID.randomUUID().toString();
        transferResponseDtoMap.put(transferUUID, createTransferRequestDto);
        return new TransferResponseDto(transferUUID);
    }
}
