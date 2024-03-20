package com.zb.tablereservation.review.entity;

import com.zb.tablereservation.common.entity.BaseEntity;
import com.zb.tablereservation.reservation.entity.Reservation;
import com.zb.tablereservation.review.dto.UpdateReview;
import com.zb.tablereservation.review.type.StarScore;
import com.zb.tablereservation.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@SuperBuilder
@Getter
public class Review extends BaseEntity {

    //별점
    @Enumerated(EnumType.STRING)
    private StarScore starScore;

    // 리뷰 내용
    private String content;

    // 리뷰자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    @ToString.Exclude
    @Setter
    private User reviewer;

    // 예약 id
    @OneToOne
    @JoinColumn(name="reservation_id")
    @Setter
    private Reservation reservation;

    public Review update(UpdateReview.Request request){
        this.starScore = request.getStarScore();
        this.content = request.getContent();
        return this;
    }
}
