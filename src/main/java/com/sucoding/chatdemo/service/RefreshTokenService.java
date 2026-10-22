package com.sucoding.chatdemo.service;

import com.sucoding.chatdemo.dto.auth.RefreshResult;
import com.sucoding.chatdemo.exception.custom.InvalidRefreshTokenException;
import com.sucoding.chatdemo.model.Member;
import com.sucoding.chatdemo.model.RefreshToken;
import com.sucoding.chatdemo.repository.MemberRepository;
import com.sucoding.chatdemo.repository.RefreshTokenRepository;
import com.sucoding.chatdemo.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;
    private final Long refreshExpirationMillis;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository,
                               MemberRepository memberRepository,
                               @Value("${jwt.refresh-expiration}") Long refreshExpirationMillis) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.memberRepository = memberRepository;
        this.refreshExpirationMillis = refreshExpirationMillis;
    }

    @Transactional
    public String issue(Long memberId) {
        Member member = memberRepository.getReferenceById(memberId);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setMember(member);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiresAt(LocalDateTime.now().plus(refreshExpirationMillis, ChronoUnit.MILLIS));
        refreshTokenRepository.save(refreshToken);
        return refreshToken.getToken();
    }

    @Transactional(noRollbackFor = InvalidRefreshTokenException.class)
    public RefreshResult refresh(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(InvalidRefreshTokenException::new);

        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.deleteByToken(token);
            throw new InvalidRefreshTokenException();
        }

        if (refreshTokenRepository.deleteByToken(token) == 0) {
            throw new InvalidRefreshTokenException();
        }

        Member member = refreshToken.getMember();
        CustomUserDetails userDetails = new CustomUserDetails(member);

        if (member.getDeletedAt() != null) {
            throw new InvalidRefreshTokenException();
        }

        String newRefreshToken = issue(member.getId());

        return new RefreshResult(userDetails, newRefreshToken);
    }

    @Transactional
    public int deleteExpired() {
        return refreshTokenRepository.deleteExpired(LocalDateTime.now());
    }
}
