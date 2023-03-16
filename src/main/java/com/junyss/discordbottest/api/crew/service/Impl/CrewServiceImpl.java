package com.junyss.discordbottest.api.crew.service.Impl;

import com.junyss.discordbottest.api.common.constant.DefaultResponse;
import com.junyss.discordbottest.api.crew.dao.CrewDAO;
import com.junyss.discordbottest.api.crew.model.dto.request.CrewSearchDTO;
import com.junyss.discordbottest.api.crew.model.dto.response.CrewListResponseDTO;
import com.junyss.discordbottest.api.crew.service.CrewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.junyss.discordbottest.api.common.constant.DefaultResponseMessage.MESSAGE_200;
import static com.junyss.discordbottest.api.common.constant.DefaultResponseMessage.OK;

/**
 * <b>비즈니스 로직을 처리하기 위한 Service 구현체</b>
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CrewServiceImpl implements CrewService {

    /**
     * <b>Crew 조회를 위한 Data Access Object</b>
     */
    private final CrewDAO crewDAO;

    /**
     * <b>크루 목록 조회를 위한 Method</b>
     * @param crewSearchDTO Client 에서 입력한 검색 조건을 갖은 객체
     * @return DefaultResponse<List<CrewListResponseDTO>> - 조회된 결과 목록(List)를 응답 객체에 묶어 반환
     */

    @Override
    public DefaultResponse<List<CrewListResponseDTO>> crewListSearch(CrewSearchDTO crewSearchDTO) {
        List<CrewListResponseDTO> resultArray = new ArrayList<>();

        crewDAO.findByCrewList(crewSearchDTO).stream().filter(Objects::nonNull).forEach(crewVO ->
                resultArray.add(CrewListResponseDTO.toDTO(crewVO)));

        return DefaultResponse.response(OK, MESSAGE_200, resultArray);
    }
}
