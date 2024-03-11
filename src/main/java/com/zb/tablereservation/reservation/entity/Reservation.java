package com.zb.tablereservation.reservation.entity;

import com.zb.tablereservation.common.entity.BaseEntity;
import com.zb.tablereservation.reservation.type.ReservationStatus;
import com.zb.tablereservation.store.entity.Store;
import com.zb.tablereservation.user.entity.User;
import jakarta.persistence.Enumerated;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


/**
 * 예약 정보를 나타내는 엔티티 클래스입니다.
 */
@Entity
@NoArgsConstructor
@Getter
public class Reservation extends BaseEntity {
    /**
     * 예약 일시
     */
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime reserveDatetime;

    /**
     * 방문 확인 일시
     */
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime visitedCheckDatetime;

    /**
     * 예약 상태
     */
    @Setter
    @Enumerated(EnumType.STRING)
    private ReservationStatus reserveStatus;

    /**
     * 예약 승인 시간
     */
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime approveDatetime;

    /**
     * 예약 거절 시간
     */
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime rejectDatetime;

    /**
     * 예약자
     */
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    /**
     * 예약매장
     */
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    @ToString.Exclude
    private Store store;

    @Builder
    public Reservation(LocalDateTime reserveDatetime, ReservationStatus reservationStatus, User user, Store store){
        this.reserveDatetime = reserveDatetime;
        this.reserveStatus = reservationStatus;
    }
}
