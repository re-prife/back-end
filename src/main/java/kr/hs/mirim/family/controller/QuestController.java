package kr.hs.mirim.family.controller;

import kr.hs.mirim.family.dto.request.CreateGroupRequest;
import kr.hs.mirim.family.dto.request.CreateQuestRequest;
import kr.hs.mirim.family.dto.request.QuestAcceptorRequest;
import kr.hs.mirim.family.service.GroupService;
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

    @PostMapping("/{questId}/acceptor")
    public void questAcceptor(
            @PathVariable long groupId,
            @PathVariable long questId,
            @Valid @RequestBody QuestAcceptorRequest request,
            BindingResult bindingResult) {
        questService.questAcceptor(groupId, questId, request, bindingResult);
    }
}
