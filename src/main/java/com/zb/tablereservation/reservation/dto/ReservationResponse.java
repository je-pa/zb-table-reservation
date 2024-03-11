package com.zb.tablereservation.reservation.dto;

import com.zb.tablereservation.reservation.entity.Reservation;
import com.zb.tablereservation.reservation.type.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class ReservationResponse {
    // id
    private Long reservationId;

    // 예약 일시
    private LocalDateTime reserveDatetime;

    // 방문 확인 일시
    private LocalDateTime visitedCheckDatetime;

    // 예약 상태
    private ReservationStatus reservationStatus;

    // 예약 승인 시간
    private LocalDateTime approveDatetime;

    // 예약 거절 시간
    private LocalDateTime rejectDatetime;

    // 예약자 아이디
    private Long userId;

    // 예약매장
    private Long storeId;

    public static ReservationResponse fromEntity(Reservation entity){
        return ReservationResponse.builder()
                .reservationId(entity.getId())
                .reserveDatetime(entity.getReserveDatetime())
                .visitedCheckDatetime(entity.getVisitedCheckDatetime())
                .reservationStatus(entity.getReserveStatus())
                .approveDatetime(entity.getApproveDatetime())
                .rejectDatetime(entity.getRejectDatetime())
                .userId(entity.getUser().getId())
                .storeId(entity.getStore().getId())
                .build();
    }
}
