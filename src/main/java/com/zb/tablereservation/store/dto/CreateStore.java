package com.zb.tablereservation.store.dto;

import com.zb.tablereservation.store.entity.Store;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

public class CreateStore {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        // 매니저 id
        @Positive
        private Long managerId;

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

        public Store toEntity(){
            return Store.builder()
                    .name(this.storeName)
                    .address(this.address)
                    .latitude(this.latitude)
                    .longitude(this.longitude)
                    .description(this.description)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        // 매장 id
        private Long id;

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

        public static Response fromEntity(Store store){
            return Response.builder()
                    .id(store.getId())
                    .managerName(store.getManager().getName())
                    .storeName(store.getName())
                    .address(store.getAddress())
                    .latitude(store.getLatitude())
                    .longitude(store.getLongitude())
                    .description(store.getDescription())
                    .build();
        }
    }
    private CreateStore(){}
}
