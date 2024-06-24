package ru.omon4412.minibank.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class ResponseAccountDto {
    private String accountId;
    private String accountName;
    private double amount;
}
