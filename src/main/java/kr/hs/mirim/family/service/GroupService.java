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
     * request body가 없을 경우 400 Bad request
     * 계정이 존재하지 않으면 404 Not found
     * 이미 그룹에 가입된 경우 409 conflict
     *
     * @author: m04j00
     * */
    public GroupResponse createGroup(CreateGroupRequest request, long userId) {
        validationUser(userId);
        String code = createInviteCode();
        Group group = new Group(code, request.getGroupName());
        groupRepository.save(group);
        userRepository.updateGroupId(group.getGroupId(), userId);
        return new GroupResponse(group.getGroupId(), group.getGroupInviteCode());
    }

    /*
     * 기존에 생성되어 있는 그룹 가입
     * request body가 없을 경우 400 Bad request
     * 계정이 존재하지 않으면 404 Not found
     * 이미 그룹에 가입된 경우 409 conflict
     *
     * @author: m04j00
     * */
    public GroupResponse joinGroup(JoinGroupRequest request, long userId) {
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
        Group group = getGroup(groupId);
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
        Group group = getGroup(groupId);
        if (group.getGroupReport() == null) {
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

    private Group getGroup(long groupId) {
        return groupRepository.findById(groupId).orElseThrow(() -> {
            throw new DataNotFoundException("존재하지 않는 그룹입니다.");
        });
    }

    private String createInviteCode() {
        char[] chs = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
        };
        Random random = new Random();
        String randomCode;

        do {
            StringBuilder code = new StringBuilder();
            for (int i = 0; i < 7; i++) {
                char ch = chs[random.nextInt(chs.length)];
                code.append(ch);
            }
            randomCode = code.toString();
        } while (groupRepository.existsByGroupInviteCode(randomCode));
        return randomCode;
    }
}
