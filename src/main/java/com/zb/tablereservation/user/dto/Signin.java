package com.zb.tablereservation.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class Signin {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {
        @NotBlank
        private String userId;
        @NotBlank
        private String password;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private String token;
    }
    private Signin() {
    }
}
