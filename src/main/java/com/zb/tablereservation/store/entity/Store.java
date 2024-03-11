package com.zb.tablereservation.store.entity;

import com.zb.tablereservation.common.entity.BaseEntity;
import com.zb.tablereservation.review.type.StarScore;
import com.zb.tablereservation.store.dto.UpdateStore;
import com.zb.tablereservation.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
public class Store extends BaseEntity {
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

    // 매니저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    @ToString.Exclude
    @Setter
    private User manager;

    @Builder
    public Store(String name, String address, Double latitude, Double longitude, String description) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }



    public void updateStore(UpdateStore.Request request){
        this.name = request.getStoreName();
        this.address = request.getAddress();
        this.latitude = request.getLatitude();
        this.longitude = request.getLongitude();
        this.description = request.getDescription();
    }

    public Store incrementReview(StarScore starScore){
        reviewCountNullCheck();
        starScoreNullCheck();

        Double sum = ((this.starScoreAvg * (double)this.reviewCount) + starScore.getValue());
        this.reviewCount++;
        this.starScoreAvg = sum/(double)reviewCount;

        return this;
    }

    public Store decreaseReview(StarScore starScore){
        reviewCountNullCheck();
        starScoreNullCheck();

        Double cal = ((this.starScoreAvg * (double)this.reviewCount) - starScore.getValue());
        this.reviewCount--;
        this.starScoreAvg = cal/(double)reviewCount;

        return this;
    }

    public Store updateOneReview(StarScore oldScore, StarScore newScore){
        reviewCountNullCheck();
        starScoreNullCheck();
        if(oldScore.getValue() == newScore.getValue()){
            return this;
        }

        this.starScoreAvg = ((this.starScoreAvg * (double)this.reviewCount)
                - oldScore.getValue() + newScore.getValue())
                / (double)reviewCount;

        return this;
    }


    private void reviewCountNullCheck() {
        this.reviewCount = this.reviewCount == null ? (long)0 : this.reviewCount;
    }
    private void starScoreNullCheck() {
        this.starScoreAvg = this.starScoreAvg == null ? (double)0 : this.starScoreAvg;
    }

}
