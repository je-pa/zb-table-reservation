package com.zb.tablereservation.reservation.status;

import com.zb.tablereservation.reservation.entity.Reservation;
import com.zb.tablereservation.reservation.type.ReservationStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class RequestedStatusUpdater extends ReservationStatusUpdater{
    private static final Long LIMIT_TIME_MINUTES = -10L;

    public RequestedStatusUpdater(Reservation reservation, Long currentUserId) {
        super(reservation, currentUserId, ReservationStatus.REQUESTED);
    }

    @Override
    boolean isAvailableTime() {
        return LocalDateTime.now().isBefore(getReservation().getReserveDatetime().plusMinutes(LIMIT_TIME_MINUTES));
    }

    @Override
    boolean isAvailableUser() {
        return isReserver();
    }

    @Override
    boolean isAvailableStatus() {
        return getReservation().getReserveStatus()==null;
    }
}
