package com.junyss.discordbottest.api.crew.model.dto.enumuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum SearchType {

    INDEX("idx", "고유번호"),
    ID("id", "계정"),
    JOIN_DATE("joinDate", "날짜"),
    NAME("name", "이름"),
    EMAIL("email", "이메일");

    private String description;
    private String searchTypeKOREAN;
}
