package com.vinsguru.playground.sec08.dto;

import java.util.UUID;

public record Payment(int orderId,
                      int amount,
                      UUID paymentId) {
}
