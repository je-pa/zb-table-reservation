package com.zb.tablereservation.reservation.repository;

import com.zb.tablereservation.reservation.entity.Reservation;
import com.zb.tablereservation.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Page<Reservation> findAllByStoreAndReserveDatetimeBetween(Store store, Pageable pageable, LocalDateTime start, LocalDateTime end);
}
