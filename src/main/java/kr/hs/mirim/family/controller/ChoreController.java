package kr.hs.mirim.family.controller;

import kr.hs.mirim.family.dto.request.CreateChoreRequest;
import kr.hs.mirim.family.service.ChoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups/{groupId}/chores")
public class ChoreController {
    private final ChoreService choreService;

    @PostMapping
    public void createChore(@PathVariable long groupId, @Valid @RequestBody CreateChoreRequest createChoreRequest, BindingResult bindingResult){
        choreService.createChore(groupId, createChoreRequest, bindingResult);
    }
}
