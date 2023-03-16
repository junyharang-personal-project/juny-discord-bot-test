package com.junyss.discordbottest.api.common.constant;

/**
 * <b>Controller에서 Response를 최종적으로 만들 때, Custom 하게 HTTP Status Code 이용하기 위한 Class</b>
 */

public final class DefaultResponseMessage {

    /* Status Code */
    public static final int OK = 200;
    public static final int CREATE_SUCCESS = 201;
    public static final int BAD_REQUEST = 400;
    public static final int UN_AUTHORIZATION = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int SERVER_ERROR = 500;
    public static final int ALREADY_CREW_INFO_SAVE = 900;

    /* 영어 */
    public static final String MESSAGE_200 = "OK";
    public static final String MESSAGE_201 = "Create Success";
    public static final String MESSAGE_400 = "Bad Request";
    public static final String MESSAGE_401 = "Un Authorization";
    public static final String MESSAGE_403 = "Forbidden";
    public static final String MESSAGE_404 = "Not Found";
    public static final String MESSAGE_500 = "Internal Server Error";
    public static final String MESSAGE_900 = "A value already registered in Database";
}
