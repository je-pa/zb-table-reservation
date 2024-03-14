package com.zb.tablereservation.reservation.dto;

import com.zb.tablereservation.reservation.entity.Reservation;
import com.zb.tablereservation.reservation.type.ReservationStatus;
import lombok.*;

import java.time.LocalDateTime;

public class CreateReservation {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        // 예약 일시
        private LocalDateTime reserveDatetime;

        // 예약 매장 아이디
        private Long storeId;

        public Reservation toEntity(){
            return Reservation.builder()
                    .reserveDatetime(reserveDatetime)
                    .reservationStatus(ReservationStatus.REQUESTED)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Response {
        // 예
        private Long reservationId;

        private Long userId;

        private Long storeId;

        public static CreateReservation.Response fromEntity(Reservation reservation){
            return Response.builder()
                    .reservationId(reservation.getId())
                    .userId(reservation.getUser().getId())
                    .storeId(reservation.getStore().getId())
                    .build();
        }
    }
    private CreateReservation(){}
}
