package ru.omon4412.minibank.dto;

import lombok.*;

@Data
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAccountDto {
    private String accountId;
    private String accountName;
    private double amount;
}
