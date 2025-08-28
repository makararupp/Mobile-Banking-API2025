package com.mk.mbanking.base;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorApi<T>(
        boolean status,
        Integer code,
        String message,
        LocalDateTime timestamp,
        T errors
) {
}
