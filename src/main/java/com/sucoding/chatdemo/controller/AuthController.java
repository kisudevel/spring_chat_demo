package com.sucoding.chatdemo.controller;

import com.sucoding.chatdemo.dto.auth.LoginRequest;
import com.sucoding.chatdemo.dto.auth.LoginResponse;
import com.sucoding.chatdemo.dto.auth.RefreshResult;
import com.sucoding.chatdemo.dto.member.MemberCreateRequest;
import com.sucoding.chatdemo.exception.custom.InvalidRefreshTokenException;
import com.sucoding.chatdemo.model.Member;
import com.sucoding.chatdemo.security.CustomUserDetails;
import com.sucoding.chatdemo.security.JwtProvider;
import com.sucoding.chatdemo.security.RefreshTokenCookie;
import com.sucoding.chatdemo.service.AuthService;
import com.sucoding.chatdemo.service.MemberService;
import com.sucoding.chatdemo.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenCookie refreshTokenCookie;
    private final MemberService memberService;

    @PostMapping("/public/auth/signup")
    public ResponseEntity<LoginResponse> signup(@Valid @RequestBody MemberCreateRequest memberCreateRequest) {
        Member member = memberService.createMember(memberCreateRequest);

        String token = jwtProvider.createToken(new CustomUserDetails(member));
        String refreshToken = refreshTokenService.issue(member.getId());
        ResponseCookie cookie = refreshTokenCookie.create(refreshToken);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new LoginResponse(token, "Bearer"));
    }

    @PostMapping("/public/auth/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String token = jwtProvider.createToken(Objects.requireNonNull(customUserDetails));

        String refreshToken = refreshTokenService.issue(customUserDetails.getMemberId());
        ResponseCookie cookie = refreshTokenCookie.create(refreshToken);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new LoginResponse(token, "Bearer"));
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        authService.logout(userDetails.getMemberId());

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.expire().toString())
                .build();
    }

    @PostMapping("/public/auth/refresh")
    public ResponseEntity<LoginResponse> refreshToken(
            @CookieValue(name = RefreshTokenCookie.NAME, required = false) String refreshToken
    ) {
        if (refreshToken == null) {
            throw new InvalidRefreshTokenException();
        }

        RefreshResult result = refreshTokenService.refresh(refreshToken);
        String token = jwtProvider.createToken(result.userDetails());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.create(result.refreshToken()).toString())
                .body(new LoginResponse(token, "Bearer"));
    }
}
