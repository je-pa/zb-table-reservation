package com.zb.tablereservation.reservation.status;

import com.zb.tablereservation.exception.ExceptionCode;
import com.zb.tablereservation.reservation.entity.Reservation;
import com.zb.tablereservation.reservation.type.ReservationStatus;

public abstract class ReservationStatusUpdater {
    private final Reservation reservation;

    private final Long currentUserId;

    private final ReservationStatus newStatus;

    protected ReservationStatusUpdater(Reservation reservation, Long currentUserId, ReservationStatus newStatus) {
        this.reservation = reservation;
        this.currentUserId = currentUserId;
        this.newStatus = newStatus;
    }

    public final Reservation updateStateTemplateMethod(){
        // 가능한 시간인가?
        checkIfAvailableTimeAndExceptionIfNot();

        // 가능한 유저인가?
        checkIfAvailableUserAndExceptionIfNot();

        // 가능한 상태인가?
        checkIfAvailableStatusAndExceptionIfNot();

        // 상태 업데이트
        updateReservationStatus();

        return this.reservation;
    }

    private void checkIfAvailableTimeAndExceptionIfNot(){
        if(!isAvailableTime()){
            throw new RuntimeException(ExceptionCode.INVALID_TIME.getMessage());
        }
    }

    private void checkIfAvailableUserAndExceptionIfNot(){
        if(!isAvailableUser()){
            throw new RuntimeException(
                    ExceptionCode.RESERVATION_STATUS_PERMISSION_DENIED.getMessage());
        }
    }

    private void checkIfAvailableStatusAndExceptionIfNot(){
        if(!isAvailableStatus()){
            throw new RuntimeException(
                    ExceptionCode.RESERVATION_STATUS_NOT_MODIFIABLE.getMessage()
            );
        }
    }
    private void updateReservationStatus() {
        this.reservation.setReserveStatus(this.newStatus);
    }
    abstract boolean isAvailableTime();
    abstract boolean isAvailableUser();
    abstract boolean isAvailableStatus();


    protected boolean isManager() {
        return this.currentUserId.equals(this.reservation.getStore().getManager().getId());
    }

    protected boolean isReserver(){
        return this.currentUserId.equals(this.reservation.getUser().getId());
    }
    protected Reservation getReservation(){
        return this.reservation;
    }
}
