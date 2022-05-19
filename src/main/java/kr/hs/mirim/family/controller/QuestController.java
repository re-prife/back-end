package kr.hs.mirim.family.controller;

import kr.hs.mirim.family.dto.request.CreateQuestRequest;
import kr.hs.mirim.family.service.QuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PostMapping("/{questId}/acceptor/{acceptorId}")
    public void questAcceptor(
            @PathVariable long groupId,
            @PathVariable long questId,
            @PathVariable long acceptorId) {
        questService.questAcceptor(groupId, questId, acceptorId);
    }
}
