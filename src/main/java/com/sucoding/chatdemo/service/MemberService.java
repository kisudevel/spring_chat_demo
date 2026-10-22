package com.sucoding.chatdemo.service;

import com.sucoding.chatdemo.dto.member.MemberCreateRequest;
import com.sucoding.chatdemo.dto.member.MemberMeResponse;
import com.sucoding.chatdemo.enums.MemberRole;
import com.sucoding.chatdemo.exception.custom.DuplicateMemberException;
import com.sucoding.chatdemo.exception.custom.MemberNotFoundException;
import com.sucoding.chatdemo.mapper.MemberMapper;
import com.sucoding.chatdemo.model.Member;
import com.sucoding.chatdemo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    public MemberMeResponse getCurrentMember(Long memberId) {
        Member member = memberRepository.findByIdAndDeletedAtIsNull(memberId)
                .orElseThrow(MemberNotFoundException::new);
        return memberMapper.toMeResponse(member);
    }

    @Transactional
    public Member createMember(MemberCreateRequest memberCreateRequest) {
        if (memberRepository.existsByEmail(memberCreateRequest.getEmail())) {
            throw DuplicateMemberException.duplicateEmail();
        }

        if (memberRepository.existsByNicknameIgnoreCase(memberCreateRequest.getNickname())) {
            throw DuplicateMemberException.duplicateNickname();
        }

        Member member = memberMapper.toEntity(memberCreateRequest);
        member.setPassword(passwordEncoder.encode(memberCreateRequest.getPassword()));
        member.setRole(MemberRole.USER);
        return memberRepository.save(member);
    }
}
