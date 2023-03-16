package com.junyss.discordbottest.api.crew.service;

import com.junyss.discordbottest.api.common.constant.DefaultResponse;
import com.junyss.discordbottest.api.crew.model.dto.request.CrewSearchDTO;
import com.junyss.discordbottest.api.crew.model.dto.response.CrewListResponseDTO;

import java.util.List;

/**
 * <b>비즈니스 로직을 처리하기 위한 Service</b>
 */
public interface CrewService {

    /**
     * <b>크루 목록 조회를 위한 Method</b>
     * @param crewSearchDTO Client 에서 입력한 검색 조건을 갖은 객체
     * @return 조회된 결과 목록(List)를 응답 객체에 묶어 반환
     */

    DefaultResponse<List<CrewListResponseDTO>> crewListSearch (CrewSearchDTO crewSearchDTO);
}
