package com.example.smallpeopleblog.dto;

import com.example.smallpeopleblog.constant.Role;
import com.example.smallpeopleblog.entity.Member;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class MemberUpdateDto {

    private Long id;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요.")
    private String password;

    private String password2;

    @Pattern(regexp = "[0-9]{10,11}", message = "핸드폰 번호는 10~11자리의 숫자로 입력해주세요.")
    private String phone;

    private String gender;

    private double point;

    private String sns;

    private Role role;

    @AssertTrue(message = "비밀번호가 일치하지 않습니다.")
    public boolean isPasswordDoubleChecked() {
        if (password != null && password2 != null) {
            return password.equals(password2);
        }
        return false;
    }

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .phone(phone)
                .gender(gender)
                .sns(sns)
                .role(role)
                .point(point)
                .build();
    }

    public static MemberUpdateDto of(Member member) {
        return MemberUpdateDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .phone(member.getPhone())
                .gender(member.getGender())
                .sns(member.getSns())
                .role(member.getRole())
                .point(member.getPoint())
                .build();
    }

}