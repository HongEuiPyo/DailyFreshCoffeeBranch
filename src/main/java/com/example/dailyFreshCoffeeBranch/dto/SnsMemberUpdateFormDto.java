package com.example.dailyFreshCoffeeBranch.dto;

import com.example.dailyFreshCoffeeBranch.constant.Role;
import com.example.dailyFreshCoffeeBranch.domain.Member;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.thymeleaf.util.StringUtils;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class SnsMemberUpdateFormDto {

    private Long id;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    private String email;

    @Pattern(regexp = "[0-9]{10,11}", message = "핸드폰 번호는 10~11자리의 숫자로 입력해주세요.")
    private String phone;

    private String gender;

    private double point;

    private String sns;

    private Role role;

    private String roadAddress;

    private String latitude;

    private String longitude;

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .email(email)
                .name(name)
                .phone(phone)
                .gender(gender)
                .sns(sns)
                .role(role)
                .point(point)
                .build();
    }

    public static SnsMemberUpdateFormDto of(Member member) {
        String roadAddress = "";
        String latitude = "";
        String longitude = "";

        if (member.getAddress() != null) {
            roadAddress = member.getAddress().getRoadAddress();
            latitude = member.getAddress().getLatitude();
            longitude = member.getAddress().getLongitude();
        }
        return SnsMemberUpdateFormDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .phone(member.getPhone())
                .gender(member.getGender())
                .sns(member.getSns())
                .role(member.getRole())
                .point(member.getPoint())
                .roadAddress(roadAddress)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

}