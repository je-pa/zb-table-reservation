package com.zb.tablereservation.store.dto;

import com.zb.tablereservation.store.dao.StoreProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class StoreListResponse implements StoreProjection {
    // 매장아이디
    Long storeId;

    // 매장명
    private String storeName;

    // 주소
    private String address;

    // 리뷰 개수
    private Long reviewCount;

    // 별점 평균
    private Double starScoreAvg;

    // 설명
    private String description;

    // 거리
    private Double distance;

    // 매니저 이름
    private String managerName;

    public static StoreListResponse fromStoreProjection(StoreProjection projection){
        return StoreListResponse.builder()
                .storeId(projection.getStoreId())
                .storeName(projection.getStoreName())
                .address(projection.getAddress())
                .reviewCount(projection.getReviewCount())
                .starScoreAvg(projection.getStarScoreAvg())
                .description(projection.getDescription())
                .distance(projection.getDistance())
                .managerName(projection.getManagerName())
                .build();
    }
}
