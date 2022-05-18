package kr.hs.mirim.family.controller;

import kr.hs.mirim.family.dto.request.CreateGroupRequest;
import kr.hs.mirim.family.dto.request.JoinGroupRequest;
import kr.hs.mirim.family.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/groups")
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/{userId}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createGroup(@Valid @RequestBody CreateGroupRequest request, @PathVariable long userId, BindingResult bindingResult) {
        groupService.createGroup(request, userId, bindingResult);
    }

    @PostMapping(value = "/join/{userId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void joinGroup(@Valid @RequestBody JoinGroupRequest request, @PathVariable long userId, BindingResult bindingResult) {
        groupService.joinGroup(request, userId, bindingResult);
    }
}
