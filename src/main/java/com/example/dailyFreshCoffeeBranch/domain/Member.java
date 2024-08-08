package com.example.dailyFreshCoffeeBranch.domain;

import com.example.dailyFreshCoffeeBranch.constant.Role;
import com.example.dailyFreshCoffeeBranch.dto.MemberFormDto;
import com.example.dailyFreshCoffeeBranch.dto.MemberUpdateFormDto;
import com.example.dailyFreshCoffeeBranch.dto.SnsMemberUpdateFormDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Member extends BaseEntity implements UserDetails {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String password;

    @Column(nullable = false)
    private String name;

    @Column
    private String phone;

    @Column
    private String gender;

    @Column
    private String sns;

    @Column(columnDefinition = "varchar(255) DEFAULT 'Y'")
    private String useYn;

    @Column(columnDefinition = "double DEFAULT '0'")
    private Double point;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "member") // FK가 안만들어짐
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToOne(mappedBy = "member")
    @JoinColumn(name = "address_id")
    private Address address;

    public MemberFormDto toDto() {
        return MemberFormDto.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .phone(phone)
                .gender(gender)
                .sns(sns)
                .point(point)
                .role(role)
                .build();
    }

    public void update(MemberFormDto memberFormDto) {
        email = memberFormDto.getEmail();
        password = memberFormDto.getPassword();
        name = memberFormDto.getName();
        phone = memberFormDto.getPhone();
        gender = memberFormDto.getGender();
        sns = memberFormDto.getSns();
        role = memberFormDto.getRole();
    }

    public Member update(String name, String sns) {
        this.name = name;
        if (sns != null) {this.sns = sns;}
        return this;
    }

    public void updateWithMemberUpdateDto(MemberUpdateFormDto memberUpdateFormDto) {
        email = memberUpdateFormDto.getEmail();
        password = memberUpdateFormDto.getPassword();
        name = memberUpdateFormDto.getName();
        phone = memberUpdateFormDto.getPhone();
        gender = memberUpdateFormDto.getGender();
        sns = memberUpdateFormDto.getSns();
        role = memberUpdateFormDto.getRole();
    }

    public void updateWithSnsMemberUpdateDto(SnsMemberUpdateFormDto snsMemberUpdateFormDto) {
        email = snsMemberUpdateFormDto.getEmail();
        name = snsMemberUpdateFormDto.getName();
        phone = snsMemberUpdateFormDto.getPhone();
        gender = snsMemberUpdateFormDto.getGender();
        sns = snsMemberUpdateFormDto.getSns();
        role = snsMemberUpdateFormDto.getRole();
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setAddress(Address address) {this.address = address;}

    public void addPoint(double point) {
        this.point += point;
    }

    public double payPoint(double point) {
        this.point -= point;
        return this.point;
    }

    public void delete() {
        this.useYn = "N";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roleList = new ArrayList<>();
        roleList.add(new SimpleGrantedAuthority(this.role.toString()));
        return roleList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}