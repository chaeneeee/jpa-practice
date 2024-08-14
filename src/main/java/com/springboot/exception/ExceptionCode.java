package com.springboot.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "Member not found"),
    MEMBER_EXISTS(409, "Member exists"),
    COFFEE_NOT_FOUND(404, "Coffee not found"),
    COFFEE_CODE_EXISTS(409, "Coffee Code exists"),
    ORDER_NOT_FOUND(404, "Order not found"),
    CANNOT_CHANGE_ORDER(403, "Order can not change"),
    NOT_IMPLEMENTATION(501, "Not Implementation"),
    NOT_COMMENT(104 ,"Comment not found"),
    BOARD_NOT_FOUND(409,"BOARD_NOT_FOUND"),
    INVALID_MEMBER_STATUS(400, "Invalid member status"),// TO 추가된 부분
    CAN_NOT_CHANGE_STATUS (402, "Can not change poster status"),
    CAN_NOT_FOUND_LIKE (413, "Can not found comment");


    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
