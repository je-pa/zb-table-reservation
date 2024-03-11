package com.zb.tablereservation.review.dto;

import com.zb.tablereservation.review.entity.Review;
import com.zb.tablereservation.review.type.StarScore;
import lombok.*;

public class CreateReview {
    @Getter
    @Setter
    @NoArgsConstructor(force = true)
    @AllArgsConstructor
    public static class Request {
        //별점
        private final StarScore starScore;

        // 리뷰 내용
        private String content;

        // 예약 id
        private Long reservationId;

        public Review toEntity(){
            return Review.builder()
                    .starScore(this.starScore)
                    .content(this.content)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
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

        public static CreateReview.Response fromEntity(Review review){
            return Response.builder()
                    .id(review.getId())
                    .starScore(review.getStarScore())
                    .content(review.getContent())
                    .reservationId(review.getReservation().getId())
                    .userName(review.getReservation().getUser().getName())
                    .storeName(review.getReservation().getStore().getName())
                    .build();
        }
    }
    private CreateReview(){}
}
