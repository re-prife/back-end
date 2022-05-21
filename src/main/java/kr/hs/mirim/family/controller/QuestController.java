package kr.hs.mirim.family.controller;

import kr.hs.mirim.family.dto.request.CreateQuestRequest;
import kr.hs.mirim.family.dto.response.QuestListResponse;
import kr.hs.mirim.family.service.QuestService;
import lombok.RequiredArgsConstructor;
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
            @Valid @RequestBody CreateQuestRequest request,
            BindingResult bindingResult,
            @PathVariable long groupId,
            @PathVariable long userId
    ) {
        questService.createQuest(groupId, userId, request, bindingResult);
    }

    @GetMapping
    public List<QuestListResponse> questList(@PathVariable long groupId) {
        return questService.questList(groupId);
    }

    @PostMapping("/{questId}/acceptor/{acceptorId}")
    public void questAcceptor(
            @PathVariable long groupId,
            @PathVariable long questId,
            @PathVariable long acceptorId) {
        questService.questAcceptor(groupId, questId, acceptorId);
    }
}
