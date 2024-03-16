package com.zb.tablereservation.reservation.status;

import com.zb.tablereservation.reservation.entity.Reservation;
import com.zb.tablereservation.reservation.type.ReservationStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class CompleteStatusUpdater extends ReservationStatusUpdater{
    private static final List<ReservationStatus> AVAILABLE_STATUS = Arrays.asList(ReservationStatus.ARRIVED);
    private static final Long LIMIT_TIME_MINUTES = 0L;

    public CompleteStatusUpdater(Reservation reservation, Long currentUserId) {
        super(reservation, currentUserId, ReservationStatus.COMPLETED);
    }

    @Override
    boolean isAvailableTime() {
        return LocalDateTime.now().isAfter(getReservation().getReserveDatetime().plusMinutes(LIMIT_TIME_MINUTES));
    }

    @Override
    boolean isAvailableUser() {
        return isManager();
    }

    @Override
    boolean isAvailableStatus() {
        ReservationStatus currentStatus = getReservation().getReserveStatus();
        return AVAILABLE_STATUS.stream()
                .anyMatch(status -> status == currentStatus);
    }

}
