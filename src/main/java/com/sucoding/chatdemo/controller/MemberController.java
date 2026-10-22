package com.sucoding.chatdemo.controller;

import com.sucoding.chatdemo.dto.member.MemberMeResponse;
import com.sucoding.chatdemo.security.CustomUserDetails;
import com.sucoding.chatdemo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/me")
    public ResponseEntity<MemberMeResponse> getCurrentMember(@AuthenticationPrincipal CustomUserDetails userDetails) {
        MemberMeResponse memberMeResponse = memberService.getCurrentMember(userDetails.getMemberId());
        return ResponseEntity.ok(memberMeResponse);
    }
}
