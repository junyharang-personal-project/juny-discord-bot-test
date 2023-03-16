package com.junyss.discordbottest.api.crew.model.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CrewVO {

    /**
     * <b>고유 번호</b>
     */
    private Long idx;

    /**
     * <b>계정</b>
     */
    private String id;

    /**
     * <b>합류일</b>
     */
    private Timestamp joinDate;

    /**
     * <b>이름</b>
     */
    private String name;

    /**
     * <b>이메일</b>
     */
    private String email;
}
