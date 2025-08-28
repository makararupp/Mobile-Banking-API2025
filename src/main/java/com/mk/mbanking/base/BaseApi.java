package com.mk.mbanking.base;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BaseApi<T>(
        boolean status,
        Integer code,
        String message,
        LocalDateTime timestamp,
        T data
) {
}
