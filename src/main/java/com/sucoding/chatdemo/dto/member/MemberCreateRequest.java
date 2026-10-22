package com.sucoding.chatdemo.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class MemberCreateRequest {
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @Size(max = 50, message = "이메일은 50자 이하로 작성해주세요.")
    private String email;

    @NotNull(message = "비밀번호는 필수입니다.")
    @Size(min = 4, max = 20, message = "비밀번호는 4자 이상 20자 이하로 작성해주세요.")
    private String password;

    @NotBlank(message = "닉네임은 필수입니다.")
    @Size(max = 20, message = "닉네임은 20자 이하로 작성해주세요.")
    private String nickname;

    public void setEmail(String email) {
        this.email = email == null ? null : email.strip().toLowerCase();
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.strip();
    }
}
