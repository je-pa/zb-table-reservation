package com.zb.tablereservation.reservation.status;

import com.zb.tablereservation.reservation.entity.Reservation;
import com.zb.tablereservation.reservation.type.ReservationStatus;

import java.util.Arrays;
import java.util.List;

public class RejectStatusUpdater extends ReservationStatusUpdater{
    private static final List<ReservationStatus> AVAILABLE_STATUS = Arrays.asList(ReservationStatus.REQUESTED);


    public RejectStatusUpdater(Reservation reservation, Long currentUserId) {
        super(reservation, currentUserId, ReservationStatus.REJECTED);
    }

    @Override
    boolean isAvailableTime() {
        return true;
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
