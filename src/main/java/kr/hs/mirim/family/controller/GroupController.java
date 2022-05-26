package kr.hs.mirim.family.controller;

import kr.hs.mirim.family.dto.request.CreateGroupRequest;
import kr.hs.mirim.family.dto.request.ReportRequest;
import kr.hs.mirim.family.dto.response.UserListResponse;
import kr.hs.mirim.family.dto.request.JoinGroupRequest;
import kr.hs.mirim.family.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/groups")
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/{userId}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createGroup(@Valid @RequestBody CreateGroupRequest request, @PathVariable long userId, BindingResult bindingResult) {
        groupService.createGroup(request, userId, bindingResult);
    }

    @PostMapping("/join/{userId}")
    public void joinGroup(@Valid @RequestBody JoinGroupRequest request, @PathVariable long userId, BindingResult bindingResult) {
        groupService.joinGroup(request, userId, bindingResult);
    }

    @GetMapping("/{groupId}/{userId}")
    public List<UserListResponse> userList(@PathVariable Long groupId, @PathVariable Long userId) {
        return groupService.userList(groupId, userId);
    }

    @PutMapping("/{groupId}/report")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateGroupReport(@PathVariable Long groupId, @Valid @RequestBody ReportRequest request, BindingResult bindingResult){
        groupService.updateGroupReport(groupId, request, bindingResult);
    }
}
