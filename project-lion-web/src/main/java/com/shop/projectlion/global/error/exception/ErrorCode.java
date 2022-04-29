package com.shop.projectlion.global.error.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // 인증
    ALREADY_REGISTERED_MEMBER(400, "이미 가입된 회원 입니다."),
    MISMATCHED_PASSWORD(401, "패스워드가 일치하지 않습니다."),
    LOGIN_ERROR(401, "아이디 또는 비밀번호를 확인해주세요"),
    NOT_EXIST_REP_IMG(500, "첫번째 상품 이미지는 필수 입력 값 입니다."),
    ITEM_REGISTER_ERROR(501, "상품 등록에 실패하였습니다.")
    ;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private int status;
    private String message;

}