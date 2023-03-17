package com.junyss.discordbottest.common.util;

import com.junyss.discordbottest.common.SpringBootAPICaller;
import com.junyss.discordbottest.api.common.constant.APIUriInfo;
import com.junyss.discordbottest.api.common.exception.CreateExceptionMessage;
import com.junyss.discordbottest.api.crew.model.dto.request.CrewSearchDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * <b>Discord Bot이 명령어를 통해 받은 내용을 토대로 URL을 만드는 객체</b>
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpUtil {

    /**
     * <b>검색 조건 없이 크루 목록을 조회할 때 호출 되는 Method로 기본 URL을 만들어 API 호출 Method를 호출한다.</b>
     * @param httpMethodType HTTP Method Type (예: GET, POST 등..)
     * @return API 호출을 통해 얻은 결과값
     */

    public static String crewByAll(String httpMethodType) {
        String exceptionMessage = CreateExceptionMessage.makeExceptionMessage();

        try {
            return SpringBootAPICaller.callApi(httpMethodType, new URL(APIUriInfo.DEFAULT_URL + APIUriInfo.CREW_PREFIX_URI + APIUriInfo.CREW_LIST_SEARCH));
        } catch (IOException ioException) {
            log.error(exceptionMessage + ioException.getMessage());
        }
        return exceptionMessage;
    }

    /**
     * <b>검색 조건을 가지고, 크루 목록을 조회할 때 호출 되는 Method URL 만드는 Method를 조건을 가지고 호출하여 API 호출 Method를 호출한다.</b>
     * @param crewSearchDTO 검색 조건을 담은 DTO 객체
     * @param httpMethodType HTTP Method Type (예: GET, POST 등..)
     * @return API 호출을 통해 얻은 결과값
     */


    public static String crewBySearchOption(CrewSearchDTO crewSearchDTO, String httpMethodType) {
        String exceptionMessage = CreateExceptionMessage.makeExceptionMessage();

        try {
            return SpringBootAPICaller.callApi(httpMethodType, new URL(makeURL(crewSearchDTO)));
        } catch (IOException ioException) {
            log.error(exceptionMessage + ioException.getMessage());
        }
        return exceptionMessage;
    }

    /**
     * <b>검색 조건을 통해 URL을 만드는 Method</b>
     * @param crewSearchDTO 검색 조건을 담은 DTO 객체
     * @return 만들어진 문자열형 URL 값
     */

    private static String makeURL (CrewSearchDTO crewSearchDTO) {
        String defaultUrl = APIUriInfo.DEFAULT_URL + APIUriInfo.CREW_PREFIX_URI + APIUriInfo.CREW_LIST_SEARCH;


        if (crewSearchDTO.getSearchType() != null && crewSearchDTO.getSearchWord() != null && crewSearchDTO.getStartDate() != null && crewSearchDTO.getEndDate() != null) {
            defaultUrl += "?searchType=";
            defaultUrl += URLEncoder.encode(crewSearchDTO.getSearchType(), StandardCharsets.UTF_8);
            defaultUrl += "&searchWord=";
            defaultUrl += URLEncoder.encode(crewSearchDTO.getSearchWord(), StandardCharsets.UTF_8);
            defaultUrl += "&startDate=";
            defaultUrl += URLEncoder.encode(crewSearchDTO.getStartDate(), StandardCharsets.UTF_8);
            defaultUrl += "&endDate=";
            defaultUrl += URLEncoder.encode(crewSearchDTO.getEndDate(), StandardCharsets.UTF_8);

        } else if (crewSearchDTO.getSearchType() != null && crewSearchDTO.getSearchWord() != null) {
            defaultUrl += "?searchType=";
            defaultUrl += URLEncoder.encode(crewSearchDTO.getSearchType(), StandardCharsets.UTF_8);
            defaultUrl += "&searchWord=";
            defaultUrl += URLEncoder.encode(crewSearchDTO.getSearchWord(), StandardCharsets.UTF_8);

        } else if (crewSearchDTO.getStartDate() != null && crewSearchDTO.getEndDate() != null) {
            defaultUrl += "?startDate=";
            defaultUrl += URLEncoder.encode(crewSearchDTO.getStartDate(), StandardCharsets.UTF_8);
            defaultUrl += "&endDate=";
            defaultUrl += URLEncoder.encode(crewSearchDTO.getEndDate(), StandardCharsets.UTF_8);
        }

        return defaultUrl;
    }
}
