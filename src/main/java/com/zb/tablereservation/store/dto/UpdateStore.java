package com.zb.tablereservation.store.dto;

import com.zb.tablereservation.store.entity.Store;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class UpdateStore {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        // 매장 id
        private Long storeId;

        // 매장명
        @NotBlank
        private String storeName;

        // 주소
        @NotBlank
        private String address;

        // x좌표
        @NotNull
        private Double latitude;

        // y좌표
        @NotNull
        private Double longitude;

        // 설명
        @NotBlank
        private String description;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        // 매니저명
        private String managerName;

        // 매장명
        private String storeName;

        // 주소
        private String address;

        // x좌표
        private Double latitude;

        // y좌표
        private Double longitude;

        // 설명
        private String description;

        public static UpdateStore.Response fromEntity(Store store){
            return UpdateStore.Response.builder()
                    .managerName(store.getManager().getName())
                    .storeName(store.getName())
                    .address(store.getAddress())
                    .latitude(store.getLatitude())
                    .longitude(store.getLongitude())
                    .description(store.getDescription())
                    .build();
        }
    }
    private UpdateStore(){}
}
