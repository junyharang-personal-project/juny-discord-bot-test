package com.junyss.discordbottest.api.crew.dao;

import com.junyss.discordbottest.api.crew.model.dto.request.CrewSearchDTO;
import com.junyss.discordbottest.api.crew.model.vo.CrewVO;

import java.util.List;

/**
 * <b>크루 관리 Mybatis를 이용한 Data Access Object</b>
 */

public interface CrewDAO {

    /**
     * <b>크루 목록 조회를 위한 Method</b>
     * @param crewSearchDTO Client 에서 입력한 검색 조건을 갖은 객체
     * @return List<CrewVO> - 조회된 결과 목록(List)를 응답 객체에 묶어 반환
     */

    List<CrewVO> findByCrewList(CrewSearchDTO crewSearchDTO);
}
