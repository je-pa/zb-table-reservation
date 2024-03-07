package com.zb.tablereservation.user.entity;

import com.zb.tablereservation.common.entity.BaseEntity;
import com.zb.tablereservation.user.type.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    // 사용자 이름
    private String name;

    // 사용자 아이디
    private String userId;

    // 비밀번호
    private String password;

    // 전화번호
    private String phoneNumber;

    // 권한
    @Enumerated(EnumType.STRING)
    private Role role;
}
