package com.zb.tablereservation.reservation.type;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReservationStatus {
    REQUEST("예약 요청됨"),
    REJECTED("예약 거절됨"),
    APPROVED("예약 승인됨"),
    ARRIVED("방문 도착 완료"),
    COMPLETED("사용 완료");

    private final String description; // 상태 설명
}
