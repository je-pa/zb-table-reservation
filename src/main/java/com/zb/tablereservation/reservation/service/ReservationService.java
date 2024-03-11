package com.zb.tablereservation.reservation.service;

import com.zb.tablereservation.exception.ExceptionCode;
import com.zb.tablereservation.reservation.dto.CreateReservation;
import com.zb.tablereservation.reservation.dto.ReservationResponse;
import com.zb.tablereservation.reservation.entity.Reservation;
import com.zb.tablereservation.reservation.repository.ReservationRepository;
import com.zb.tablereservation.reservation.type.ReservationStatus;
import com.zb.tablereservation.security.util.MySecurityUtil;
import com.zb.tablereservation.store.entity.Store;
import com.zb.tablereservation.store.repository.StoreRepository;
import com.zb.tablereservation.user.entity.User;
import com.zb.tablereservation.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final Long RESERVATION_LIMIT_MINUTES = 10L;
    private final Long ARRIVE_LIMIT_MINUTES = 10L;

    public CreateReservation.Response createReservation(CreateReservation.Request request) {
        if(!isAvailableTimeBefore(request.getReserveDatetime(), RESERVATION_LIMIT_MINUTES)){
            throw new RuntimeException(ExceptionCode.RESERVATION_NOT_AVAILABLE_TIME.getMessage());
        }
        Reservation reservation = request.toEntity();
        reservation.setStore(getStoreById(request.getStoreId()));
        reservation.setUser(getCurrentUser());
        reservation.setReserveStatus(ReservationStatus.REQUEST);

        Reservation savedReservation = reservationRepository.save(reservation);

        return CreateReservation.Response.fromEntity(savedReservation);
    }

    public Page<ReservationResponse> findAllByStoreId(Long storeId, Pageable pageable, LocalDate date) {
        Store store = getStoreById(storeId);
        verifyManager(store.getManager().getId());
        LocalDateTime end = date.plusDays(1).atStartOfDay();
        LocalDateTime start = end.minusDays(1);
        return reservationRepository.findAllByStoreAndReserveDatetimeBetween(store, pageable, start, end).map(ReservationResponse::fromEntity);
    }

    public ReservationResponse approveReservation(Long id) {
        verifyManager(getReservationById(id).getStore().getId());
        Reservation reservation = getReservationById(id);
        if(!reservation.getReserveStatus().name().equals(ReservationStatus.REQUEST.name())){
            throw new RuntimeException(ExceptionCode.APPROVE_NOT_AVAILABLE_STATE.getMessage());
        }
        return ReservationResponse.fromEntity(
                updateReservationStatus(reservation, ReservationStatus.APPROVED));
    }

    public ReservationResponse rejectReservation(Long id) {
        verifyManager(getReservationById(id).getStore().getId());
        Reservation reservation = getReservationById(id);
        if(!reservation.getReserveStatus().name().equals(ReservationStatus.REQUEST.name())){
            throw new RuntimeException(ExceptionCode.REJECT_NOT_AVAILABLE_STATE.getMessage());
        }

        return ReservationResponse.fromEntity(
                updateReservationStatus(reservation, ReservationStatus.REJECTED));
    }

    public ReservationResponse arriveReservation(Long id) {
        Reservation reservation = getReservationById(id);
        if(!isAvailableTimeBefore(
                reservation.getReserveDatetime(), ARRIVE_LIMIT_MINUTES)){
            throw new RuntimeException(
                    ExceptionCode.RESERVATION_NOT_AVAILABLE_TIME.getMessage());
        }
        if(!reservation.getReserveStatus().name().equals(ReservationStatus.APPROVED.name())){
            throw new RuntimeException(ExceptionCode.REJECT_NOT_AVAILABLE_STATE.getMessage());
        }
        return ReservationResponse.fromEntity(
                updateReservationStatus(reservation, ReservationStatus.ARRIVED));
    }

    public ReservationResponse completeReservation(Long id) {
        Reservation reservation = getReservationById(id);
        if(!reservation.getReserveStatus().name().equals(ReservationStatus.REQUEST.name())){
            throw new RuntimeException(ExceptionCode.REJECT_NOT_AVAILABLE_STATE.getMessage());
        }
        return ReservationResponse.fromEntity(
                updateReservationStatus(reservation, ReservationStatus.COMPLETED));
    }

    private Reservation updateReservationStatus(Reservation reservation, ReservationStatus status) {
        reservation.setReserveStatus(status);

        return reservationRepository.save(reservation);
    }

    private Reservation getReservationById(Long id){
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        ExceptionCode.RESERVATION_NOT_FOUND.getMessage()));
    }

    private Store getStoreById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        ExceptionCode.STORE_NOT_FOUND.getMessage()));
    }

    private User getCurrentUser() {
        return userRepository.findByUserId(MySecurityUtil.getCustomUserDetails().getUsername())
                .orElseThrow(()-> new RuntimeException(
                        ExceptionCode.USER_NOT_FOUND.getMessage()));
    }

    public boolean isAvailableTimeBefore(LocalDateTime reservationDateTime, Long limitTime) {
        return LocalDateTime.now().isBefore(
                reservationDateTime.minusMinutes(limitTime));
    }

    private void verifyManager(Long managerId) {
        if(!getCurrentUser().getId().equals(managerId)){
            throw new RuntimeException(
                    ExceptionCode.LOGIN_USER_MANAGER_MISMATCH.getMessage());
        }
    }
}