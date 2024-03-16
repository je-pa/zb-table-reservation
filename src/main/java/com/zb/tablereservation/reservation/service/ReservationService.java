package com.zb.tablereservation.reservation.service;

import com.zb.tablereservation.exception.ExceptionCode;
import com.zb.tablereservation.reservation.dto.CreateReservation;
import com.zb.tablereservation.reservation.dto.ReservationResponse;
import com.zb.tablereservation.reservation.entity.Reservation;
import com.zb.tablereservation.reservation.repository.ReservationRepository;
import com.zb.tablereservation.reservation.status.*;
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

    public CreateReservation.Response createReservation(CreateReservation.Request request) {
        Reservation reservation = request.toEntity();
        reservation.setStore(getStoreById(request.getStoreId()));
        reservation.setUser(getCurrentUser());

        return CreateReservation.Response.fromEntity(
                updateReservationStatus(
                        new RequestedStatusUpdater(
                                reservation, getCurrentUser().getId())));
    }

    public Page<ReservationResponse> findAllByStoreId(Long storeId, Pageable pageable, LocalDate date) {
        Store store = getStoreById(storeId);
        verifyManager(store.getManager().getId());

        LocalDateTime end = date.plusDays(1).atStartOfDay();
        LocalDateTime start = end.minusDays(1);

        return reservationRepository
                .findAllByStoreAndReserveDatetimeBetween(store, pageable, start, end)
                .map(ReservationResponse::fromEntity);
    }

    public ReservationResponse approveReservation(Long id) {
        return ReservationResponse.fromEntity(
                updateReservationStatus(
                        new ApproveStatusUpdater(
                                getReservationById(id), getCurrentUser().getId())));
    }

    public ReservationResponse rejectReservation(Long id) {
        return ReservationResponse.fromEntity(
                updateReservationStatus(
                        new RejectStatusUpdater(
                                getReservationById(id), getCurrentUser().getId())));
    }

    public ReservationResponse arriveReservation(Long id) {
        return ReservationResponse.fromEntity(
                updateReservationStatus(
                        new ArriveStatusUpdater(
                                getReservationById(id), getCurrentUser().getId())));
    }

    public ReservationResponse completeReservation(Long id) {
        return ReservationResponse.fromEntity(
                updateReservationStatus(
                        new CompleteStatusUpdater(
                                getReservationById(id), getCurrentUser().getId())));
    }

    private Reservation updateReservationStatus(ReservationStatusUpdater updater) {
        return reservationRepository.save(updater.updateStateTemplateMethod());
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

    private void verifyManager(Long managerId) {
        if(!getCurrentUser().getId().equals(managerId)){
            throw new RuntimeException(
                    ExceptionCode.LOGIN_USER_MANAGER_MISMATCH.getMessage());
        }
    }
}