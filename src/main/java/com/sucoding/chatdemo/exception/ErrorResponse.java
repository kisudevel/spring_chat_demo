package com.sucoding.chatdemo.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ErrorResponse(String message, Map<String, String> errors) {
    public ErrorResponse(String message) {
        this(message, Map.of());
    }
}
