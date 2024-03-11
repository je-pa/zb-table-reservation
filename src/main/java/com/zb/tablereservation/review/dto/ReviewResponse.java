package com.zb.tablereservation.review.dto;

import com.zb.tablereservation.review.entity.Review;
import com.zb.tablereservation.review.type.StarScore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ReviewResponse {
    // 리뷰 id
    private Long id;

    //별점
    private StarScore starScore;

    // 리뷰 내용
    private String content;

    // 예약 id
    private Long reservationId;

    // 유저 이름
    private String userName;

    // 매장 이름
    private String storeName;

    public static ReviewResponse fromEntity(Review review){
        return ReviewResponse.builder()
                .id(review.getId())
                .starScore(review.getStarScore())
                .content(review.getContent())
                .reservationId(review.getReservation().getId())
                .userName(review.getReservation().getUser().getName())
                .storeName(review.getReservation().getStore().getName())
                .build();
    }
}
