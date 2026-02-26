package com.vinsguru.playground.sec08.dto;

public record Notification(int orderId,
                           NotificationChannel channel,
                           String recipient) {
}
