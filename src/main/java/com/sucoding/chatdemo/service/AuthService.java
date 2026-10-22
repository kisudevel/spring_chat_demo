package com.sucoding.chatdemo.service;

import com.sucoding.chatdemo.exception.custom.MemberNotFoundException;
import com.sucoding.chatdemo.model.Member;
import com.sucoding.chatdemo.repository.MemberRepository;
import com.sucoding.chatdemo.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final Clock clock;

    @Transactional
    public void logout(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        refreshTokenRepository.deleteByMemberId(memberId);

        member.setTokenInvalidBefore(
                LocalDateTime.now(clock).truncatedTo(ChronoUnit.SECONDS).plusSeconds(1));
    }
}
