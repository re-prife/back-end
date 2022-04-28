package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.request.CreateGroupRequest;
import kr.hs.mirim.family.dto.response.UserListResponse;
import kr.hs.mirim.family.entity.user.repository.UserRepository;
import kr.hs.mirim.family.entity.group.Group;
import kr.hs.mirim.family.entity.group.repository.GroupRepository;
import kr.hs.mirim.family.exception.DataNotFoundException;
import kr.hs.mirim.family.exception.FormValidateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public void createGroup(CreateGroupRequest requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new FormValidateException("유효하지 않은 형식입니다.");
        }
        existsUser(requestDto.getUserId());
        String code = createInviteCode();
        Group group = new Group(code, requestDto.getGroupName());
        groupRepository.save(group);
        userRepository.updateGroupId(group.getGroupId(), requestDto.getUserId());
    }

    /*
    * 그룹에 속한 회원을 조회하는 기능
    * 해당 API를 요청한 회원의 정보는 반환하지 않는다.
    *
    * API를 요청한 회원을 제외한 그룹에 속한 회원의 id, name, nickname, imageName을 list로 전달한다.
    * 그룹이 없을 경우 404 not found
    * 계정이 없을 경우 404 not found
    * */
    @Transactional
    public List<UserListResponse> userList(long groupId, long userId) {
        if (!groupRepository.existsById(groupId)) {
            throw new DataNotFoundException("존재하지 않는 그룹입니다.");
        }
        existsUser(userId);
        return userRepository.userList(groupId, userId);
    }

    private void existsUser(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new DataNotFoundException("존재하지 않는 회원입니다.");
        }
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
