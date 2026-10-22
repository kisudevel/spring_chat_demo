package com.sucoding.chatdemo.dto.auth;

import com.sucoding.chatdemo.security.CustomUserDetails;

public record RefreshResult(CustomUserDetails userDetails, String refreshToken) {
}
