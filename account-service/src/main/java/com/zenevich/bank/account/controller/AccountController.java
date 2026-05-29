package com.zenevich.bank.account.controller;

import com.zenevich.bank.account.dto.AccountResponse;
import com.zenevich.bank.account.dto.CreateAccountRequest;
import com.zenevich.bank.account.entity.Account;
import com.zenevich.bank.account.repository.AccountRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(
            @Valid @RequestBody CreateAccountRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        Account account = Account.builder()
                .userId(userDetails.getUsername())
                .currency(request.getCurrency())
                .build();

        accountRepository.save(account);

        return ResponseEntity.ok(toResponse(account));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getMyAccounts(
            @AuthenticationPrincipal UserDetails userDetails) {

        List<Account> accounts = accountRepository.findByUserId(userDetails.getUsername());
        List<AccountResponse> responses = accounts.stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(
            @PathVariable String id,
            @AuthenticationPrincipal UserDetails userDetails) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (!account.getUserId().equals(userDetails.getUsername())) {
            throw new RuntimeException("Access denied");
        }

        return ResponseEntity.ok(toResponse(account));
    }

    private AccountResponse toResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .createdAt(account.getCreatedAt() != null ? account.getCreatedAt().toString() : null)
                .build();
    }
}