package com.vinsguru.playground.sec18.dto;

public record TransactionRequest(String account,
                                 Integer amount,
                                 TransactionType type) {
}
