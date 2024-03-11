package com.zb.tablereservation.reservation.controller;

import com.zb.tablereservation.reservation.dto.CreateReservation;
import com.zb.tablereservation.reservation.dto.ReservationResponse;
import com.zb.tablereservation.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * 사용자가 예약을 생성한다.
     * 현재 시간이 예약 요청 시간의 10분전 이후면 예약이 불가능하다.
     * @param request 예약 정보를 받는다.
     * @return 생성된 예약 정보를 반환한다.
     */
    @PostMapping("")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CreateReservation.Response> createReservation(@RequestBody CreateReservation.Request request) {
        return ResponseEntity.ok(reservationService.createReservation(request));
    }

    /**
     * 파트너가 특정날짜에 해당하는 예약내역을 확인한다.
     * @param storeId 파트너의 매장 아이디
     * @param page 페이지 넘버
     * @param size 한페이지에서 볼 예약 건수
     * @param date 예약 확인할 날짜
     * @return 특정날짜에 해당하는 예약내역을 반환한다.
     */
    @GetMapping("")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<Page<ReservationResponse>> readListByStoreIdAndReserveDate(
            @RequestParam Long storeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam LocalDate date){
        return ResponseEntity.ok(reservationService.findAllByStoreId(storeId, PageRequest.of(page,size), date));
    }

    /**
     * 매장 주인이 예약을 승인한다.
     * 로그인 유저가 매장 주인이 아니면 불가능하다.
     * @param reservationId 예약 아이디
     * @return 승인한 예약의 정보를 반환한다.
     */
    @PutMapping("/{reservationId}/approve")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<ReservationResponse> approveReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.approveReservation(reservationId));
    }

    /**
     * 매장 주인이 예약을 거절한다.
     * 로그인 유저가 매장 주인이 아니면 불가능하다.
     * @param reservationId 예약 아이디
     * @return 거절한 예약의 정보를 반환한다.
     */
    @PutMapping("/{reservationId}/reject")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<ReservationResponse> rejectReservation(@PathVariable Long reservationId) {

        return ResponseEntity.ok(reservationService.rejectReservation(reservationId));
    }

    /**
     * 예약한 유저가 방문처리한다.
     * 현재 시간이 예약 요청 시간의 10분전 이후면 방문처리가 불가능하다.
     * 예약한 유저만이 방문처리를 할 수 있다.
     * @param reservationId 예약아이디
     * @return 방문처리된 예약 정보를 반환한다.
     */
    @PutMapping("/{reservationId}/arrive")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReservationResponse> arriveReservation(@PathVariable Long reservationId) {

        return ResponseEntity.ok(reservationService.arriveReservation(reservationId));
    }

    /**
     * 파트너가 사용완료로 체크한다.
     * 로그인 유저가 매장 주인이 아니면 불가능하다.
     * @param reservationId 예약아이디
     * @return 사용완료된 예약 정보를 반환한다.
     */
    @PutMapping("/{reservationId}/complete")
    @PreAuthorize("hasRole('PARTNER')")
    public ResponseEntity<ReservationResponse> completeReservation(@PathVariable Long reservationId) {
        return ResponseEntity.ok(reservationService.completeReservation(reservationId));
    }
}
