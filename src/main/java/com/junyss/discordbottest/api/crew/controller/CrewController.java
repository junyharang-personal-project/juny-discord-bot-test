package com.junyss.discordbottest.api.crew.controller;

import com.junyss.discordbottest.api.common.constant.APIUriInfo;
import com.junyss.discordbottest.api.common.constant.DefaultResponse;
import com.junyss.discordbottest.api.crew.service.CrewService;
import com.junyss.discordbottest.api.crew.model.dto.request.CrewSearchDTO;
import com.junyss.discordbottest.api.crew.model.dto.response.CrewListResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(APIUriInfo.CREW_PREFIX_URI)
@RestController
public class CrewController {

    private final CrewService crewService;

    @Operation(summary = "모든 크루 지원자 정보 조회", description = "모든 크루 지원자 지원서 정보 조회 위한 API Method.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "조회 결과 없음"),
            @ApiResponse(responseCode = "500", description = "서버 문제"),
    })
    @GetMapping(APIUriInfo.CREW_LIST_SEARCH)
    public DefaultResponse<List<CrewListResponseDTO>> crewListSearch(@ModelAttribute(value = "crewSearchDTO") CrewSearchDTO crewSearchDTO) {
        return crewService.crewListSearch(crewSearchDTO);
    }
}
