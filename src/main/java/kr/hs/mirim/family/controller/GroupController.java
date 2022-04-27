package kr.hs.mirim.family.controller;

import kr.hs.mirim.family.dto.request.CreateGroupRequestDto;
import kr.hs.mirim.family.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@Controller
@RequestMapping(value="/groups")
public class GroupController {
    private final GroupService groupService;

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createGroup(@RequestBody CreateGroupRequestDto createGroupRequestDto){
        groupService.createGroup(createGroupRequestDto);
    }
}
