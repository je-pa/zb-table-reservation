package com.zb.tablereservation.review.service;

import com.zb.tablereservation.exception.ExceptionCode;
import com.zb.tablereservation.reservation.entity.Reservation;
import com.zb.tablereservation.reservation.repository.ReservationRepository;
import com.zb.tablereservation.reservation.type.ReservationStatus;
import com.zb.tablereservation.review.dto.CreateReview;
import com.zb.tablereservation.review.dto.DeleteReviewResponse;
import com.zb.tablereservation.review.dto.ReviewResponse;
import com.zb.tablereservation.review.dto.UpdateReview;
import com.zb.tablereservation.review.entity.Review;
import com.zb.tablereservation.review.repository.ReviewRepository;
import com.zb.tablereservation.security.util.MySecurityUtil;
import com.zb.tablereservation.store.entity.Store;
import com.zb.tablereservation.store.repository.StoreRepository;
import com.zb.tablereservation.user.entity.User;
import com.zb.tablereservation.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public CreateReview.Response create(CreateReview.Request reviewRequest) {
        Reservation reservation = getReservationById(reviewRequest.getReservationId());
        if(!isReserver(reservation)){
            throw new RuntimeException(ExceptionCode.WRITE_REVIEW_PERMISSION_DENIED.getMessage());
        }
        if(!reservation.getReserveStatus().name().equals(ReservationStatus.COMPLETED.name())){
            throw new RuntimeException(ExceptionCode.REVIEW_NOT_AVAILABLE_STATUS.getMessage());
        }
        Review review = reviewRequest.toEntity();
        review.setReservation(reservation);

        Store store = reservation.getStore();
        storeRepository.save(store.incrementReview(reviewRequest.getStarScore()));

        return CreateReview.Response.fromEntity(reviewRepository.save(review));
    }

    public ReviewResponse get(Long id) {
        return ReviewResponse.fromEntity(getReviewById(id));
    }

    @Transactional
    public UpdateReview.Response update(UpdateReview.Request request) {
        Review review = getReviewById(request.getId());

        if(!isReserver(review.getReservation())){
            throw new RuntimeException(ExceptionCode.MODIFY_REVIEW_PERMISSION_DENIED.getMessage());
        }
        Store store = review.getReservation().getStore();
        storeRepository.save(store.updateOneReview(review.getStarScore(), request.getStarScore()));
        return UpdateReview.Response.fromEntity(reviewRepository.save(review.update(request)));
    }


    @Transactional
    public DeleteReviewResponse delete(Long reviewId) {
        Review review = getReviewById(reviewId);
        Reservation reservation = review.getReservation();
        if(!isReserver(reservation) && !isStoreManager(reservation)){
            throw new RuntimeException(ExceptionCode.DELETE_REVIEW_PERMISSION_DENIED.getMessage());
        }
        Store store = reservation.getStore();
        storeRepository.save(store.decreaseReview(review.getStarScore()));
        reviewRepository.deleteById(reviewId);
        return DeleteReviewResponse.builder().id(reviewId).build();
    }

    private boolean isReserver(Reservation reservation){
        return getCurrentUser().getId().equals(reservation.getUser().getId());
    }

    private boolean isStoreManager(Reservation reservation){
        return getCurrentUser().getId().equals(reservation.getStore().getManager().getId());
    }
    private Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ExceptionCode.REVIEW_NOT_FOUND.getMessage()));
    }
    private Reservation getReservationById(Long id){
        return reservationRepository.findById(id).orElseThrow(
                ()-> new RuntimeException(
                        ExceptionCode.RESERVATION_NOT_FOUND.getMessage())
        );
    }

    private User getCurrentUser() {
        return userRepository.findByUserId(MySecurityUtil.getCustomUserDetails().getUsername())
                .orElseThrow(()-> new RuntimeException(
                        ExceptionCode.USER_NOT_FOUND.getMessage()));
    }
}
