package com.sucoding.chatdemo.scheduler;

import com.sucoding.chatdemo.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenCleaner {
    private final RefreshTokenService refreshTokenService;

    @Scheduled(cron = "0 0 4 * * *")
    public void clean() {
        int deleted = refreshTokenService.deleteExpired();
        System.out.println("리프레시 토큰 " + deleted + "건을 정리했습니다.");
    }
}
