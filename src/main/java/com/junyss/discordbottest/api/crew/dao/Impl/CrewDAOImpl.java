package com.junyss.discordbottest.api.crew.dao.Impl;

import com.junyss.discordbottest.api.crew.dao.CrewDAO;
import com.junyss.discordbottest.api.crew.mapper.CrewManagementMapper;
import com.junyss.discordbottest.api.crew.model.dto.request.CrewSearchDTO;
import com.junyss.discordbottest.api.crew.model.vo.CrewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <b>크루 관리 Mybatis를 이용한 Data Access Object 구현체</b>
 */

@RequiredArgsConstructor
@Repository
public class CrewDAOImpl implements CrewDAO {

    /**
     * <b>MyBatis Mapper 사용을 위한 Mapper 객체</b>
     */
    private final CrewManagementMapper crewManagementMapper;

    /**
     * <b>크루 목록 조회를 위한 Method</b>
     * @param crewSearchDTO Client 에서 입력한 검색 조건을 갖은 객체
     * @return List<CrewVO> - 조회된 결과 목록(List)를 응답 객체에 묶어 반환
     */
    @Transactional(readOnly = true)
    @Override
    public List<CrewVO> findByCrewList(CrewSearchDTO crewSearchDTO) {
        return crewManagementMapper.findByCrewList(crewSearchDTO);
    }
}
