package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.request.CreateGroupRequestDto;
import kr.hs.mirim.family.entity.user.repository.UserRepository;
import kr.hs.mirim.family.entity.group.Group;
import kr.hs.mirim.family.entity.group.repository.GroupRepository;
import kr.hs.mirim.family.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;

@Transactional
@RequiredArgsConstructor
@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public void createGroup(CreateGroupRequestDto requestDto) {
        existsUser(requestDto.getUserId());
        String code = createInviteCode();
        Group group = new Group(code, requestDto.getGroupName());
        groupRepository.save(group);
        userRepository.updateGroupId(group.getGroupId(), requestDto.getUserId());
    }

    private void existsUser(long userId) {
        if (!userRepository.existsByUserId(userId)) {
            throw new DataNotFoundException("User Not Found");
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
