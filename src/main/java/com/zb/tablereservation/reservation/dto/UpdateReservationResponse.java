package com.zb.tablereservation.reservation.dto;

import com.zb.tablereservation.reservation.entity.Reservation;
import com.zb.tablereservation.reservation.type.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
public class UpdateReservationResponse {
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

    public static UpdateReservationResponse fromEntity(Reservation entity){
        return UpdateReservationResponse.builder()
                .reserveDatetime(entity.getReserveDatetime())
                .visitedCheckDatetime(entity.getVisitedCheckDatetime())
                .reservationStatus(entity.getReservationStatus())
                .approveDatetime(entity.getApproveDatetime())
                .rejectDatetime(entity.getRejectDatetime())
                .userId(entity.getUser().getId())
                .storeId(entity.getStore().getId())
                .build();
    }
}
