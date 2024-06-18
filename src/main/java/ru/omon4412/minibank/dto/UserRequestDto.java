package ru.omon4412.minibank.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotNull
    private Long userId;
    @NotNull
    private String userName;
}
