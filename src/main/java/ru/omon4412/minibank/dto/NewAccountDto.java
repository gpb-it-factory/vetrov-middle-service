package ru.omon4412.minibank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewAccountDto {
    @NotNull
    @NotBlank
    private String accountName;
}
