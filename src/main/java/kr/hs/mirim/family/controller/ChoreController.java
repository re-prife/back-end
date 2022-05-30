package kr.hs.mirim.family.controller;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hs.mirim.family.dto.request.CreateChoreRequest;
import kr.hs.mirim.family.dto.request.ChoreCertifyReactionRequest;
import kr.hs.mirim.family.dto.response.ChoreListMonthResponse;
import kr.hs.mirim.family.dto.response.ChoreListOneDayResponse;
import kr.hs.mirim.family.service.ChoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Tag(name = "CHORE", description = "집안일 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/groups/{groupId}/chores")
public class ChoreController {
    private final ChoreService choreService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "집안일 생성 성공"),
            @ApiResponse(responseCode = "400", description = "Request 형식이 유효하지 않을 경우"),
            @ApiResponse(responseCode = "404", description = "groupId가 존재하지 않을 경우\n\n회원이 그룹에 속하지 않는 경우"),
            @ApiResponse(responseCode = "409", description = "집안일 날짜에 집안일 담당자가 이미 할당된 경우")
    })
    @Operation(tags = "CHORE", summary = "집안일 생성", description = "집안일을 생성하는 API")
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createChore(@ApiParam(value = "집안일을 생성할 그룹의 ID") @PathVariable long groupId,
                            @Valid @RequestBody CreateChoreRequest createChoreRequest,
                            BindingResult bindingResult) {
        choreService.createChore(groupId, createChoreRequest, bindingResult);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "날짜에 해당하는 집안일 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "date 형식이 유효하지 않을 경우"),
            @ApiResponse(responseCode = "404", description = "groupId가 존재하지 않을 경우")
    })
    @Operation(tags = "CHORE", summary = "집안일 하루 목록 조회", description = "날짜에 해당하는 집안일을 조회하는 API")
    @GetMapping("/one-day")
    public ChoreListOneDayResponse choreListOneDay(@ApiParam(value = "집안일을 조회할 그룹의 ID") @PathVariable long groupId,
                                                   @ApiParam(value = "집안일을 조회할 날짜 (년-월-일)") @RequestParam String date) {
        return choreService.choreListOneDay(groupId, date);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "달에 해당하는 집안일 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "date 형식이 유효하지 않을 경우"),
            @ApiResponse(responseCode = "404", description = "groupId가 존재하지 않을 경우")
    })
    @Operation(tags = "CHORE", summary = "집안일 달 목록 조회", description = "달에 해당하는 집안일을 조회하는 API")
    @GetMapping
    public ChoreListMonthResponse choreListMonth(@ApiParam(value = "집안일을 생성할 그룹의 ID") @PathVariable long groupId,
                                                 @ApiParam(value = "집안일을 조회할 날짜 (년-월)") @RequestParam String date) {
        return choreService.choreListMonth(groupId, date);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "집안일 상태를 REQUEST로 갱신 성공"),
            @ApiResponse(responseCode = "404", description = "groupId, choreId가 존재하지 않을 경우\n\n집안일이 그룹에 속하지 않을 경우"),
            @ApiResponse(responseCode = "409", description = "이미 인증 요청된 집안일일 경우")
    })
    @Operation(tags = "CHORE", summary = "집안일 인증 요청", description = "집안일 상태를 REQUEST로 갱신하는 API")
    @PostMapping("/{choreId}/certify")
    public void choreCertify(@ApiParam(value = "집안일이 속한 그룹의 ID") @PathVariable long groupId,
                             @ApiParam(value = "집안일 상태를 변경할 집안일의 ID") @PathVariable long choreId) {
        choreService.choreCertify(groupId, choreId);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증 요청에 대한 응답 갱신 성공"),
            @ApiResponse(responseCode = "400", description = "Request 형식이 유효하지 않을 경우"),
            @ApiResponse(responseCode = "404", description = "groupId, choreId가 존재하지 않을 경우\n\n집안일이 그룹에 속하지 않을 경우"),
            @ApiResponse(responseCode = "409", description = "집안일 상태가 이미 SUCCESS인데 REQUEST로 변경하려는 경우\n\n집안일 상태가 REQUEST가 아닐 경우")
    })
    @Operation(tags = "CHORE", summary = "집안일 인증 요청 응답", description = "인증 요청에 대한 응답을 갱신하는 API")
    @PostMapping("/{choreId}/reaction")
    public void choreCertifyReaction(@ApiParam(value = "집안일이 속한 그룹의 ID") @PathVariable long groupId,
                                     @ApiParam(value = "집안일 상태를 변경할 집안일의 ID") @PathVariable long choreId,
                                     @Valid @RequestBody ChoreCertifyReactionRequest choreCertifyReactionRequest,
                                     BindingResult bindingResult) {
        choreService.choreCertifyReaction(groupId, choreId, choreCertifyReactionRequest, bindingResult);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "집안일 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "groupId, choreId가 존재하지 않을 경우"),
            @ApiResponse(responseCode = "409", description = "집안일이 그룹에 속하지 않을 경우"),
            @ApiResponse(responseCode = "405", description = "인증이 안료된 집안일을 삭제하려는 경우")
    })
    @Operation(tags = "CHORE", summary = "집안일 인증 요청", description = "집안일 상태를 REQUEST로 갱신하는 API")
    @DeleteMapping("/{choreId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteChore(
            @ApiParam(value = "집안일을 생성할 그룹의 ID") @PathVariable long groupId,
            @ApiParam(value = "삭제할 집안일의 ID") @PathVariable long choreId
    ) {
        choreService.deleteChore(groupId, choreId);
    }
}
