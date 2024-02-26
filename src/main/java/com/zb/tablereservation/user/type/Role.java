package com.zb.tablereservation.user.type;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {
    ROLE_USER("회원"),
    ROLE_PARTNER("매장관리자");

    private final String roleName;
}
