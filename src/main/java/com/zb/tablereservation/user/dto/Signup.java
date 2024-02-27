package com.zb.tablereservation.user.dto;

import com.zb.tablereservation.user.entity.User;
import com.zb.tablereservation.user.type.Role;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class Signup {
    @Getter
    @AllArgsConstructor
    public static class Request {
        private String name;
        private String userId;
        private String password;
        @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$"
                , message = "올바른 휴대폰 번호 형식이 아닙니다.")
        private String phoneNumber;
        private Role role;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String name;

        private String userId;

        private String password;

        private String phoneNumber;

        private Role role;

        public static Response fromEntity(User user) {
            return Response.builder()
                    .name(user.getName())
                    .userId(user.getUserId())
                    .password(user.getPassword())
                    .phoneNumber(user.getPhoneNumber())
                    .role(user.getRole())
                    .build();
        }
    }
    private Signup() {
    }
}
