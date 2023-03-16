package com.junyss.discordbottest.api.crew.model.dto.request;

import com.junyss.discordbottest.api.crew.model.dto.enumuration.SearchType;
import lombok.Data;

@Data
public class CrewSearchDTO {

    /**
     * <b>검색 조건(종류)</b>
     */
    private SearchType searchType;

    /**
     * <b>검색어</b>
     */
    private String searchWord;

    /**
     * <b>날짜 검색을 위한 시작 날짜</b>
     */

    private String startDate;

    /**
     * <b>날짜 검색을 위한 끝 날짜</b>
     */

    private String endDate;

    /**
     * <b>Client에서 입력된 값을 Enum으로 처리한 뒤 해당 값을 알맞는 문자열로 바꿔 Data Base 처리 시 사용되게 하기 위한 Getter</b>
     * @return String Enum Type의 실제 Data Base 처리 시 사용될 문자열
     */
    public String getSearchType() {
        if (searchType != null) {
            return this.searchType.getDescription();
        }
        return "";
    }

    /**
     * <b>Client에서 입력된 검색 타입을 Enum Type에 맞춰 저장하기 위한 Setter</b>
     * @param searchType Client에서 입력된 검색 타입
     */

    public void setSearchType(String searchType) {

        switch (searchType) {
            case "idx":
                this.searchType = SearchType.INDEX;
                break;
            case "id":
                this.searchType = SearchType.ID;
                break;
            case "joinDate":
                this.searchType = SearchType.JOIN_DATE;
                break;
            case "name":
                this.searchType = SearchType.NAME;
                break;
            default:
                this.searchType = SearchType.EMAIL;
                break;
        }
    }
}