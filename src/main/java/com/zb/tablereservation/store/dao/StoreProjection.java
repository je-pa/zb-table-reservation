package com.zb.tablereservation.store.dao;

public interface StoreProjection {
    // 상점 ID
    Long getStoreId();

    // 상점 이름
    String getStoreName();

    // 상점 주소
    String getAddress();

    // 리뷰 수
    Long getReviewCount();

    // 별점 평균
    Double getStarScoreAvg();

    // 상점 설명
    String getDescription();

    // 매니저 이름
    String getManagerName();

    // 현재 위치로부터의 거리 (단위: km)
    Double getDistance();
}
