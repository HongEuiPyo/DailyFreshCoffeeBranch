package com.example.dailyFreshCoffeeBranch.dto;

import com.example.dailyFreshCoffeeBranch.constant.Role;
import com.example.dailyFreshCoffeeBranch.entity.Member;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class MemberDto {

    private Long id;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요.")
    private String password;

    private String password2;

    @Pattern(regexp = "[0-9]{10,11}", message = "핸드폰 번호는 10~11자리의 숫자로 입력해주세요.")
    private String phone;

    @NotBlank(message = "성별은 필수 입력 값입니다.")
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

}