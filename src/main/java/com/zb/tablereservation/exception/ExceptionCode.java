package com.zb.tablereservation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    RESERVATION_NOT_AVAILABLE_TIME(400, "예약 가능한 시간이 아닙니다."),
    ARRIVAL_NOT_AVAILABLE_TIME(400, "도착 가능한 시간이 아닙니다."),
    APPROVE_NOT_AVAILABLE_STATE(400, "승인 가능한 상태가 아닙니다."),
    REJECT_NOT_AVAILABLE_STATE(400, "거절 가능한 상태가 아닙니다."),
    ARRIVE_NOT_AVAILABLE_STATE(400, "도착 가능한 상태가 아닙니다."),
    COMPLETE_NOT_AVAILABLE_STATE(400, "완료 가능한 상태가 아닙니다."),
    REVIEW_NOT_AVAILABLE_STATE(400, "리뷰 가능한 상태가 아닙니다."),

    // Unauthorized,
    AUTHENTICATION_ISSUE(401, "인증에 문제가 있습니다."),
    TOKEN_EXPIRED(401, "토큰이 만료되었습니다."),
    PASSWORD_MISMATCH(401, "비밀번호가 일치하지 않습니다."),

    // Forbidden
    WRITE_REVIEW_PERMISSION_DENIED(403,"해당 리뷰를 작성할 권한이 없습니다."),
    MODIFY_REVIEW_PERMISSION_DENIED(403,"해당 리뷰를 수정할 권한이 없습니다."),
    DELETE_REVIEW_PERMISSION_DENIED(403,"해당 리뷰를 삭제할 권한이 없습니다."),


    // NOT_FOUND
    USER_NOT_FOUND(404, "유저를 찾지 못했습니다."),
    STORE_NOT_FOUND(404, "매장을 찾지 못했습니다."),
    RESERVATION_NOT_FOUND(404, "예약을 찾지 못했습니다."),
    REVIEW_NOT_FOUND(404, "리뷰를 찾지 못했습니다."),

    // Conflict
    USER_ID_CONFLICT(409, "이미 존재하는 사용자 아이디입니다."),
    LOGIN_USER_MANAGER_MISMATCH(409, "로그인 유저와 매장의 매니저가 일치하지 않습니다.");

    private final int status;
    private final String message;
}
