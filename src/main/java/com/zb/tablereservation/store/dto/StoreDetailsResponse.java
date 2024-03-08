package com.zb.tablereservation.store.dto;

import com.zb.tablereservation.store.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class StoreDetailsResponse {
    // 매장아이디
    Long storeId;

    // 매장명
    private String name;

    // 주소
    private String address;

    // x좌표
    private Double latitude;

    // y좌표
    private Double longitude;

    // 리뷰 개수
    private Long reviewCount;

    // 별점 평균
    private Double starScoreAvg;

    // 설명
    private String description;

    // 매니저 이름
    private String managerName;

    // 매니저 전화번호
    private String phoneNumber;

    public static StoreDetailsResponse fromEntity(Store store){
        return StoreDetailsResponse.builder()
                .storeId(store.getId())
                .name(store.getName())
                .address(store.getAddress())
                .latitude(store.getLatitude())
                .longitude(store.getLongitude())
                .reviewCount(store.getReviewCount())
                .starScoreAvg(store.getStarScoreAvg())
                .description(store.getDescription())
                .managerName(store.getManager().getName())
                .phoneNumber(store.getManager().getPhoneNumber())
                .build();
    }
}
