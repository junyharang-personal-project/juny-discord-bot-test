package com.junyss.discordbottest.api.crew.model.dto.response;

import com.junyss.discordbottest.api.crew.model.vo.CrewVO;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class CrewListResponseDTO {
    private Long idx;
    private String id;
    private Timestamp joinDate;
    private String name;
    private String email;

    @Builder
    public static CrewListResponseDTO toDTO (CrewVO crewVO) {
        CrewListResponseDTO crewListResponseDTO = new CrewListResponseDTO();

        crewListResponseDTO.idx = crewVO.getIdx();
        crewListResponseDTO.id = crewVO.getId();
        crewListResponseDTO.joinDate = crewVO.getJoinDate();
        crewListResponseDTO.name = crewVO.getName();
        crewListResponseDTO.email = crewVO.getEmail();

        return crewListResponseDTO;
    }
}
