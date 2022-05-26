package kr.hs.mirim.family.controller;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups/{groupId}/chores")
public class ChoreController {
    private final ChoreService choreService;

    @PostMapping
    public void createChore(@PathVariable long groupId, @Valid @RequestBody CreateChoreRequest createChoreRequest, BindingResult bindingResult){
        choreService.createChore(groupId, createChoreRequest, bindingResult);
    }
  
    @GetMapping("/one-day")
    public ChoreListOneDayResponse choreListOneDay(@PathVariable long groupId, @RequestParam String date){
        return choreService.choreListOneDay(groupId, date);
    }

    @GetMapping
    public ChoreListMonthResponse choreListMonth(@PathVariable long groupId, @RequestParam String date){
        return choreService.choreListMonth(groupId, date);
    }

    @PostMapping("/{choreId}/certify")
    public void choreCertify(@PathVariable long groupId, @PathVariable long choreId){
        choreService.choreCertify(groupId, choreId);
    }

    @PostMapping("/{choreId}/reaction")
    public void choreCertifyReaction(@PathVariable long groupId, @PathVariable long choreId, @Valid @RequestBody ChoreCertifyReactionRequest choreCertifyReactionRequest, BindingResult bindingResult){
        choreService.choreCertifyReaction(groupId, choreId, choreCertifyReactionRequest, bindingResult);
    }

    @DeleteMapping("/{choreId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteChore(
            @PathVariable long groupId, @PathVariable long choreId
    ){
        choreService.deleteChore(groupId, choreId);
    }
}
