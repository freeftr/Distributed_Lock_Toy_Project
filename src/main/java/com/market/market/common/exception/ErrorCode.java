package com.market.market.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_REQUEST(1000, "유효하지 않은 요청입니다."),

    PRODUCT_NOT_FOUND(2000, "존재하지 않는 상품입니다."),
    PRODUCT_ALREADY_SOLD_OUT(2001, "이미 품절된 상품입니다."),
    NOT_ENOUGH_STOCK(2002, "재고가 충분하지 않습니다."),
    PRODUCT_IS_LOCKED_AOP(2003, "AOP 분산 락이 점유하고 있는 상태입니다."),

    ACCOUNT_NOT_FOUND(3000, "존재하지 않은 계좌입니다."),
    NOT_ENOUGH_BALANCE(3001, "잔액이 충분치 않습니다."),
    NOT_OWNER_OF_ACCOUNT(3002, "계좌 소유주가 아닙니다."),

    ORDER_NOT_FOUND(4000, "존재하지 않는 주문입니다."),
    UNAUTHORIZED_ORDER(4001, "주문자가 아닙니다."),
    ;

    private final int code;
    private final String message;
}
