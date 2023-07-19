package com.example.dailyFreshCoffeeBranch.entity;

import com.example.dailyFreshCoffeeBranch.dto.MemberUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Address extends BaseEntity {

    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String latitude;

    private String longitude;

    private String roadAddress;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public void update(MemberUpdateDto dto) {
        String[] array = dto.getLatlng()
                .replace("(", "")
                .replace(")", "")
                .split(",");
        this.latitude = array[0];
        this.longitude = array[2];
        this.roadAddress = dto.getRoadAddress();
    }

}