package com.zb.tablereservation.review.controller;

import com.zb.tablereservation.review.dto.CreateReview;
import com.zb.tablereservation.review.dto.DeleteReviewResponse;
import com.zb.tablereservation.review.dto.ReviewResponse;
import com.zb.tablereservation.review.dto.UpdateReview;
import com.zb.tablereservation.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    /**
     * 예약자가 리뷰를 생성한다.
     * 예약을 하고 사용완료가 되어야 리뷰 작성이 가능하다.
     * 리뷰를 쓰면 매장 점수가 수정된다.
     * @param request 리뷰 정보를 받는다.
     * @return 생성된 리뷰 정보를 반환한다.
     */
    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CreateReview.Response> create(
            @RequestBody CreateReview.Request request) {
        return ResponseEntity.ok(reviewService.create(request));
    }

    /**
     * 리뷰를 확인한다.
     * @param reviewId 확인할 리뷰의 id
     * @return id에 해당하는 리뷰 정보를 반환한다.
     */
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> get(
            @PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.get(reviewId));
    }

    /**
     * 리뷰를 수정한다.
     * 작성자만이 리뷰 업데이트가 가능하다.
     * 리뷰를 수정하면 매장의의 점수가 수정된다.
     * @param request 리뷰를 업데이트할 정보를 받는다
     * @return 업데이터된 리뷰의 정보를 반환한다.
     */
    @PutMapping("")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<UpdateReview.Response> update(
            @RequestBody UpdateReview.Request request) {
        return ResponseEntity.ok(reviewService.update(request));
    }

    /**
     * 리뷰를 삭제한다.
     * 삭제는 리뷰를 쓴 사람과 해당 매장의 매니저만 가능하다.
     * 삭제하면 매장의 점수가 수정된다.
     * @param reviewId
     * @return
     */
    @DeleteMapping("/{reviewId}")
    @PreAuthorize("hasAnyRole('USER','PARTNER')")
    public ResponseEntity<DeleteReviewResponse> delete(
            @PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.delete(reviewId));
    }
}
