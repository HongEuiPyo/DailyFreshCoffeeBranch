package com.example.smallpeopleblog.dto;

import lombok.*;

import javax.validation.constraints.Min;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class MemberPointUpDto {

    @Min(value = 1, message = "포인트를 최소 1원 이상 입금해주세요.")
    private int point;

}