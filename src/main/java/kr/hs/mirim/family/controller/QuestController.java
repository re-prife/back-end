package kr.hs.mirim.family.controller;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hs.mirim.family.dto.request.QuestRequest;
import kr.hs.mirim.family.dto.response.QuestFindOneResponse;
import kr.hs.mirim.family.dto.response.QuestResponse;
import kr.hs.mirim.family.entity.quest.Quest;
import kr.hs.mirim.family.service.NotificationService;
import kr.hs.mirim.family.service.QuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "QUEST", description = "심부름 API")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/groups/{groupId}/quests")
public class QuestController {
    private final QuestService questService;
    private final NotificationService notificationService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "심부름 생성 성공"),
            @ApiResponse(responseCode = "400", description = "Request 형식이 유효하지 않을 경우"),
            @ApiResponse(responseCode = "404", description = "groupId 또는 userId가 존재하지 않을 경우")
    })
    @Operation(tags = "QUEST", summary = "심부름 생성", description = "심부름을 생성하는 API")
    @PostMapping("/{userId}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createQuest(
            @Valid @RequestBody QuestRequest request,
            BindingResult bindingResult,
            @ApiParam(value = "심부름이 속한 그룹 ID") @PathVariable long groupId,
            @ApiParam(value = "심부름을 생성할 회원 ID") @PathVariable long userId
    ) {
        Quest quest = questService.createQuest(groupId, userId, request, bindingResult);

        notificationService.questNotification(quest);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "심부름 조회 성공"),
            @ApiResponse(responseCode = "404", description = "groupId가 존재하지 않을 경우")
    })
    @Operation(tags = "QUEST", summary = "심부름 조회", description = "심부름을 조회하는 API")
    @GetMapping
    public List<QuestResponse> questList(@ApiParam(value = "심부름이 속한 그룹 ID") @PathVariable long groupId) {
        return questService.questList(groupId);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "심부름을 수락하거나 수락 취소 성공"),
            @ApiResponse(responseCode = "404", description = "groupId가 존재하지 않을 경우\n\nquestId 또는 acceptorId가 존재하지 않거나 그룹에 속하지 않을 경우"),
            @ApiResponse(responseCode = "409", description = "이미 해결된 심부름일 경우\n\n수락자가 존재하는 심부름을 다른 사람이 수락하려는 경우\n\n심부름을 요청한 사람이 수락하려는 경우")
    })
    @Operation(tags = "QUEST", summary = "심부름 수락 / 수락 후 취소", description = "심부름을 수락하거나 수락한 후 취소하는 API")
    @PutMapping("/{questId}/acceptor/{acceptorId}")
    public void questAcceptor(
            @ApiParam(value = "심부름이 속한 그룹 ID") @PathVariable long groupId,
            @ApiParam(value = "수락하거나 수락한 심부름 ID") @PathVariable long questId,
            @ApiParam(value = "심부름을 수락하거나 수락한 회원 ID") @PathVariable long acceptorId) {
        questService.questAcceptor(groupId, questId, acceptorId);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "완료 여부 갱신 성공"),
            @ApiResponse(responseCode = "400", description = "Request 형식이 유효하지 않을 경우"),
            @ApiResponse(responseCode = "404", description = "groupId가 존재하지 않을 경우\n\nquestId 또는 requesterId가 존재하지 않거나 그룹에 속하지 않을 경우"),
            @ApiResponse(responseCode = "409", description = "요청자가 아닌 사람이 API를 호출할 경우\n\n심부름 수락자가 존재하지 않을 경우")
    })
    @Operation(tags = "QUEST", summary = "심부름 완료", description = "수락자가 심부름 완료 시 요청자가 심부름 완료 여부를 성공으로 갱신하는 API")
    @PutMapping("/{questId}/complete/{requesterId}")
    public void questCompleteCheck(
            @ApiParam(value = "심부름이 속한 그룹 ID") @PathVariable long groupId,
            @ApiParam(value = "완료할 심부름 ID") @PathVariable long questId,
            @ApiParam(value = "심부름 요청자 ID") @PathVariable long requesterId) {
        questService.questCompleteCheck(groupId, questId, requesterId);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "심부름 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "groupId가 존재하지 않을 경우\n\nquestId 또는 userId가 존재하지 않거나 그룹에 속하지 않을 경우"),
            @ApiResponse(responseCode = "405", description = "심부름 수락자가 있는 경우"),
            @ApiResponse(responseCode = "409", description = "요청자가 아닌 사람이 API를 호출할 경우")
    })
    @Operation(tags = "QUEST", summary = "심부름 삭제", description = "심부름을 삭제하는 API")
    @DeleteMapping("/{questId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteQuest(
            @ApiParam(value = "심부름이 속한 그룹 ID") @PathVariable long groupId,
            @ApiParam(value = "삭제할 심부름 ID") @PathVariable long questId,
            @ApiParam(value = "심부름 요청자 ID") @RequestParam long userId
    ) {
        questService.deleteQuest(groupId, questId, userId);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "심부름 갱신 성공"),
            @ApiResponse(responseCode = "404", description = "groupId가 존재하지 않을 경우\n\nquestId 또는 requesterId가 존재하지 않거나 그룹에 속하지 않을 경우"),
            @ApiResponse(responseCode = "405", description = "심부름 수락자가 있는 경우"),
            @ApiResponse(responseCode = "409", description = "requesterId가 심부름 요청자와 일지하지 않을 경우")
    })
    @Operation(tags = "QUEST", summary = "심부름 갱신", description = "심부름 갱신 API")
    @PutMapping("/{questId}")
    public QuestResponse updateQuest(
            @ApiParam(value = "심부름이 속한 그룹 ID") @PathVariable long groupId,
            @ApiParam(value = "갱신할 심부름 ID") @PathVariable long questId,
            @Valid @RequestBody QuestRequest request,
            @ApiParam(value = "심부름 요청자 ID") @RequestParam long requesterId
    ) {
        return questService.updateQuest(groupId, questId, request, requesterId);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "심부름 조회 성공"),
            @ApiResponse(responseCode = "404", description = "groupId가 존재하지 않을 경우\n\nquestId가 존재하지 않거나 그룹에 속하지 않을 경우")
    })
    @Operation(tags = "QUEST", summary = "심부름 상세 조회", description = "심부름 상세 조회 API")
    @GetMapping("/{questId}")
    public QuestFindOneResponse findOneQuest(@ApiParam(value = "심부름이 속한 그룹 ID") @PathVariable long groupId,
                                             @ApiParam(value = "조회할 심부름 ID") @PathVariable long questId) {

        return questService.findOneQuest(groupId, questId);
    }
}
