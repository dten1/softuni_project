package com.aipiwatch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class RequestLog {
    private final String method;
    private final String endpoint;
    private final int status;
    private final long durationMs;
    private final LocalDateTime timestamp;
}