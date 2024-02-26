package com.zb.tablereservation.user.entity;

import com.zb.tablereservation.common.entity.BaseEntity;
import com.zb.tablereservation.user.type.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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
    private String name;

    private String userId;

    private String password;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;
}
