package kr.hs.mirim.family.controller;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hs.mirim.family.dto.request.CreateGroupRequest;
import kr.hs.mirim.family.dto.request.ReportRequest;
import kr.hs.mirim.family.dto.response.GetGroupReportResponse;
import kr.hs.mirim.family.dto.response.GroupResponse;
import kr.hs.mirim.family.dto.response.UserListResponse;
import kr.hs.mirim.family.dto.request.JoinGroupRequest;
import kr.hs.mirim.family.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "GROUP", description = "그룹 API")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/groups")
public class GroupController {
    private final GroupService groupService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "그룹 생성 성공"),
            @ApiResponse(responseCode = "400", description = "Request body가 존재하지 않을 경우"),
            @ApiResponse(responseCode = "404", description = "userId가 존재하지 않을 경우"),
            @ApiResponse(responseCode = "409", description = "userId가 이미 그룹에 가입된 경우")
    })
    @Operation(tags = "GROUP", summary = "그룹 생성", description = "그룹을 생성하는 API")
    @PostMapping("/{userId}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public GroupResponse createGroup(@Valid @RequestBody CreateGroupRequest request,
                                     @ApiParam(value = "그룹을 생성할 user의 ID")
                                     @PathVariable long userId) {
        return groupService.createGroup(request, userId);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "그룹 가입 성공"),
            @ApiResponse(responseCode = "400", description = "Request body가 존재하지 않을 경우"),
            @ApiResponse(responseCode = "404", description = "userId가 존재하지 않을 경우"),
            @ApiResponse(responseCode = "409", description = "userId가 이미 그룹에 가입된 경우")
    })
    @Operation(tags = "GROUP", summary = "그룹 가입", description = "그룹에 가입하는 API")
    @PostMapping("/join/{userId}")
    public GroupResponse joinGroup(@Valid @RequestBody JoinGroupRequest request,
                                   @ApiParam(value = "그룹에 가입할 user의 ID") @PathVariable long userId) {
        return groupService.joinGroup(request, userId);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "userId 또는 groupId가 존재하지 않을 경우")
    })
    @Operation(tags = "GROUP", summary = "그룹에 속한 회원 조회", description = "내가 속한 그룹에서 나를 제외한 회원의 목록을 조회하는 API")
    @GetMapping("/{groupId}/{userId}")
    public List<UserListResponse> userList(@ApiParam(value = "조회할 group의 ID") @PathVariable Long groupId,
                                           @ApiParam(value = "API를 호출한 user의 ID") @PathVariable Long userId) {
        return groupService.userList(groupId, userId);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "그룹의 공지사항 갱신 성공"),
            @ApiResponse(responseCode = "400", description = "Request 형식이 유효하지 않을 경우"),
            @ApiResponse(responseCode = "404", description = "groupId가 존재하지 않을 경우")
    })
    @Operation(tags = "GROUP", summary = "그룹의 공지사항 갱신", description = "그룹의 공지사항을 갱신하는 API")
    @PutMapping("/{groupId}/report")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateGroupReport(@ApiParam(value = "공지사항을 갱신할 group의 ID") @PathVariable Long groupId,
                                  @Valid @RequestBody ReportRequest request,
                                  BindingResult bindingResult) {
        groupService.updateGroupReport(groupId, request, bindingResult);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "그룹의 공지사항 조회 성공"),
            @ApiResponse(responseCode = "404", description = "groupId가 존재하지 않을 경우")
    })
    @Operation(tags = "GROUP", summary = "그룹의 공지사항 조회", description = "그룹의 공지사항을 조회하는 API")
    @GetMapping("/{groupId}/report")
    public GetGroupReportResponse getGroupReport(@ApiParam(value = "공지사항을 조회할 group의 ID") @PathVariable Long groupId) {
        return groupService.getGroupReport(groupId);
    }
}
