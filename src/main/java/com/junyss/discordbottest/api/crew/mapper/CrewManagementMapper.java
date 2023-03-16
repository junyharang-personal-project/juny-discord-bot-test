package com.junyss.discordbottest.api.crew.mapper;

import com.junyss.discordbottest.api.crew.model.dto.request.CrewSearchDTO;
import com.junyss.discordbottest.api.crew.model.vo.CrewVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b>MyBatis를 이용한 DB Handling을 위한 Mapper</b>
 */
@Mapper
@Repository
public interface CrewManagementMapper {

    /**
     * <b>크루 목록 조회 및 검색을 위한 Method</b>
     * @param crewSearchDTO Client가 검색을 위해 입력한 값을 담은 DTO
     * @return List<CrewVO> - DB에서 조회된 결과 목록
     */
    List<CrewVO> findByCrewList(@Param("crewSearchDTO") CrewSearchDTO crewSearchDTO);
}
