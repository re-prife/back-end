package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.request.CreateGroupRequest;
import kr.hs.mirim.family.dto.request.JoinGroupRequest;
import kr.hs.mirim.family.dto.request.ReportRequest;
import kr.hs.mirim.family.dto.response.GetGroupReportResponse;
import kr.hs.mirim.family.dto.response.GroupResponse;
import kr.hs.mirim.family.dto.response.UserListResponse;
import kr.hs.mirim.family.entity.user.User;
import kr.hs.mirim.family.entity.user.repository.UserRepository;
import kr.hs.mirim.family.entity.group.Group;
import kr.hs.mirim.family.entity.group.repository.GroupRepository;
import kr.hs.mirim.family.exception.ConflictException;
import kr.hs.mirim.family.exception.DataNotFoundException;
import kr.hs.mirim.family.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    /*
     * 그룹 생성
     * 성공 시 그룹 초대 코드 생성 후 201
     * dto form이 일치하지 않으면 400 Bad request
     * 계정이 존재하지 않으면 404 Not found
     * 이미 그룹에 가입된 경우 409 conflict
     *
     * @author: m04j00
     * */
    public GroupResponse createGroup(CreateGroupRequest request, long userId, BindingResult bindingResult) {
        formValidate(bindingResult);
        validationUser(userId);
        String code = createInviteCode();
        Group group = new Group(code, request.getGroupName());
        groupRepository.save(group);
        userRepository.updateGroupId(group.getGroupId(), userId);
        return new GroupResponse(group.getGroupId(), group.getGroupInviteCode());
    }

    /*
     * 기존에 생성되어 있는 그룹 가입
     * dto form이 일치하지 않으면 400 Bad request
     * 계정이 존재하지 않으면 404 Not found
     * 이미 그룹에 가입된 경우 409 conflict
     *
     * @author: m04j00
     * */
    public GroupResponse joinGroup(JoinGroupRequest request, long userId, BindingResult bindingResult) {
        formValidate(bindingResult);
        validationUser(userId);

        Group group = groupRepository.findByGroupInviteCode(request.getGroupInviteCode()).orElseThrow(() ->
        {
            throw new DataNotFoundException("존재하지 않는 그룹입니다.");
        });
        userRepository.updateGroupId(group.getGroupId(), userId);
        return new GroupResponse(group.getGroupId(), group.getGroupInviteCode());
    }

    /*
     * 그룹에 속한 회원을 조회하는 기능
     * 해당 API를 요청한 회원의 정보는 반환하지 않는다.
     *
     * API를 요청한 회원을 제외한 그룹에 속한 회원의 id, name, nickname, imageName을 list로 전달한다.
     * 그룹이 없을 경우 404 not found
     * 계정이 없을 경우 404 not found
     *
     * @author: m04j00
     * */
    public List<UserListResponse> userList(long groupId, long userId) {
        if (!groupRepository.existsById(groupId)) {
            throw new DataNotFoundException("존재하지 않는 그룹입니다.");
        }
        existsUser(userId);
        return userRepository.userList(groupId, userId);
    }

    /*
     * 그룹의 공지사항을 갱신하는 기능
     *
     * 그룹이 존재하지 않을 경우 404 not found
     *
     * @author: m04j00
     * */
    @Transactional
    public void updateGroupReport(long groupId, ReportRequest request, BindingResult bindingResult) {
        formValidate(bindingResult);
        Group group = gretGroup(groupId);
        group.updateGroupReport(request.getGroupReport());
    }

    /*
     * 그룹의 공지사항을 조회하는 기능
     *
     * 그룹이 존재하지 않을 경우 404 not found
     *
     * @author: m04j00
     * */
    public GetGroupReportResponse getGroupReport(long groupId) {
        Group group = gretGroup(groupId);
        if (group.getGroupReport().isEmpty()) {
            return new GetGroupReportResponse("");
        }
        return new GetGroupReportResponse(group.getGroupReport());
    }

    private void formValidate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException("유효하지 않은 형식입니다.");
        }
    }

    private void validationUser(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new DataNotFoundException("존재하지 않는 회원입니다.");
        });
        if (user.getGroup() != null) {
            throw new ConflictException("이미 그룹에 가입된 회원입니다.");
        }
    }

    private void existsUser(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new DataNotFoundException("존재하지 않는 회원입니다.");
        }
    }

    private Group gretGroup(long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> {
            throw new DataNotFoundException("존재하지 않는 그룹입니다.");
        });
    }

    private String createInviteCode() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 7;
        Random random = new Random();
        String code;
        do {
            code = random
                    .ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
        } while (groupRepository.existsByGroupInviteCode(code));
        return code;
    }
}
