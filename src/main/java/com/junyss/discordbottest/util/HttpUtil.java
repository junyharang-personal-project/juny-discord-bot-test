package com.junyss.discordbottest.util;

import com.junyss.discordbottest.api.common.SpringBootAPICaller;
import com.junyss.discordbottest.api.common.constant.APIUriInfo;
import com.junyss.discordbottest.api.common.exception.CreateExceptionMessage;
import com.junyss.discordbottest.api.crew.model.dto.request.CrewSearchDTO;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
public class HttpUtil {

    private HttpUtil() {}

    public static String crewByAll(String httpMethodType) {
        String exceptionMessage = CreateExceptionMessage.makeExceptionMessage();

        try {
            return SpringBootAPICaller.callApi(httpMethodType, new URL(APIUriInfo.URL + APIUriInfo.CREW_PRIFIX_URI + APIUriInfo.CREW_LIST_SEARCH));
        } catch (IOException ioException) {
            log.error(exceptionMessage + ioException.getMessage());
        }
        return exceptionMessage;
    }

    public static String crewBySearchOption(CrewSearchDTO crewSearchDTO, String httpMethodType) {
        String exceptionMessage = CreateExceptionMessage.makeExceptionMessage();

        try {
            return SpringBootAPICaller.callApi(httpMethodType, new URL(makeURL(crewSearchDTO)));
        } catch (IOException ioException) {
            log.error(exceptionMessage + ioException.getMessage());
        }
        return exceptionMessage;
    }

    private static String makeURL (CrewSearchDTO crewSearchDTO) {
        String defaultUrl = APIUriInfo.URL + APIUriInfo.CREW_PRIFIX_URI + APIUriInfo.CREW_LIST_SEARCH;

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
