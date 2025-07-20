package com.market.market.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_REQUEST(1000, "유효하지 않은 요청입니다."),

    PRODUCT_NOT_FOUND(2000, "존재하지 않는 상품입니다."),
    PRODUCT_ALREADY_SOLD_OUT(2001, "이미 품절된 상품입니다."),
    ;

    private final int code;
    private final String message;
}
