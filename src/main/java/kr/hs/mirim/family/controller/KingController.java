package kr.hs.mirim.family.controller;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hs.mirim.family.dto.response.MonthKingResponse;
import kr.hs.mirim.family.dto.response.UserFindKingResponse;
import kr.hs.mirim.family.service.KingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@Tag(name = "KING", description = "왕 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/groups/{groupId}")
public class KingController {
    private final KingService kingService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이달의 왕 조회 성공"),
            @ApiResponse(responseCode = "400", description = "date 형식이 유효하지 않을 경우"),
            @ApiResponse(responseCode = "404", description = "groupId가 존재하지 않을 경우")
    })
    @Operation(tags = "KING", summary = "이달의 왕 조회", description = "이달의 왕을 조회하는 API")
    @GetMapping("/kings")
    public MonthKingResponse userKingInfo(@ApiParam(value = "이달의 왕을 조회할 그룹 ID") @PathVariable long groupId,
                                                  @ApiParam(value = "이달의 왕을 조회할 날짜 (년-월)") @RequestParam String date) {
        return kingService.userKing(groupId, date);
    }
}
