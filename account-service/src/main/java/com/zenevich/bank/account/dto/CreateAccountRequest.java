package com.zenevich.bank.account.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateAccountRequest {
    @NotBlank(message = "Currency is required")
    private String currency;
}