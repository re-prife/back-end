package kr.hs.mirim.family.controller;

import kr.hs.mirim.family.dto.request.QuestRequest;
import kr.hs.mirim.family.dto.response.QuestResponse;
import kr.hs.mirim.family.service.QuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "groups/{groupId}/quests")
public class QuestController {
    private final QuestService questService;

    @PostMapping("/{userId}")
    public void createQuest(
            @Valid @RequestBody QuestRequest request,
            BindingResult bindingResult,
            @PathVariable long groupId,
            @PathVariable long userId
    ) {
        questService.createQuest(groupId, userId, request, bindingResult);
    }

    @GetMapping
    public List<QuestResponse> questList(@PathVariable long groupId) {
        return questService.questList(groupId);
    }

    @PostMapping("/{questId}/acceptor/{acceptorId}")
    public void questAcceptor(
            @PathVariable long groupId,
            @PathVariable long questId,
            @PathVariable long acceptorId) {
        questService.questAcceptor(groupId, questId, acceptorId);
    }

    @PostMapping("/{questId}/complete/{requesterId}")
    public void questCompleteCheck(
            @PathVariable long groupId,
            @PathVariable long questId,
            @PathVariable long requesterId) {
        questService.questCompleteCheck(groupId, questId, requesterId);
    }


    @DeleteMapping("/{questId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteQuest(
            @PathVariable long groupId,
            @PathVariable long questId,
            @RequestParam long userId
    ) {
        questService.deleteQuest(groupId, questId, userId);
    }

    @PutMapping("/{questId}")
    public QuestResponse updateQuest(
            @PathVariable long groupId,
            @PathVariable long questId,
            @Valid @RequestBody QuestRequest request,
            @RequestParam long requesterId
    ) {
        return questService.updateQuest(groupId, questId, request, requesterId);
    }
}
