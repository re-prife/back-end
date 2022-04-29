package kr.hs.mirim.family.controller;

import kr.hs.mirim.family.dto.request.CreateGroupRequest;
import kr.hs.mirim.family.dto.request.JoinGroupRequest;
import kr.hs.mirim.family.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/groups")
public class GroupController {
    private final GroupService groupService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createGroup(@Valid @RequestBody CreateGroupRequest createGroupRequestDto, BindingResult bindingResult) {
        groupService.createGroup(createGroupRequestDto, bindingResult);
    }

    @PostMapping(value = "/join")
    @ResponseStatus(code = HttpStatus.OK)
    public void joinGroup(@Valid @RequestBody JoinGroupRequest request, BindingResult bindingResult) {
        groupService.joinGroup(request, bindingResult);
    }
}
