package com.junyss.discordbottest.api.common.constant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DefaultResponse<T> {

    private Integer statusCode;
    private String message;
    private T data;

    /**
     * <b>상태 코드 + 부가 설명 반환</b>
     *
     * @param statusCode Http Status Code Number
     * @param message Response Body에 응답할 Message
     * @return <T> DefaultResponse<T> Response Body 응답값
     */

    public static <T> DefaultResponse<T> response(final Integer statusCode, final String message) {
        return (DefaultResponse<T>)DefaultResponse.builder()
                .statusCode(statusCode)
                .message(message)
                .build();
    }

    /**
     * <b>상태 코드 + 부가 설명 + 응답 데이터 반환</b>
     *
     * @param statusCode Http Status Code Number
     * @param message Response Body에 응답할 Message
     * @param data 연산 뒤 처리 결과값 객체
     * @return <T> DefaultResponse<T> Response Body 응답값
     */

    public static <T> DefaultResponse<T> response(final Integer statusCode,final String message, final T data) {
        return (DefaultResponse<T>)DefaultResponse.builder()
                .statusCode(statusCode)
                .message(message)
                .data(data)
                .build();
    }
}
