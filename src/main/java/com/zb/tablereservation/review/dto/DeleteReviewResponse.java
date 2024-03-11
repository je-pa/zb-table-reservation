package com.zb.tablereservation.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class DeleteReviewResponse {
    // 삭제된 리뷰 id
    private Long id;
}
