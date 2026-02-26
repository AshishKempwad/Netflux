package com.vinsguru.playground.sec09.dto;

public record Order(int id,
                    int customerId,
                    int amount,
                    ProductType productType) {
}