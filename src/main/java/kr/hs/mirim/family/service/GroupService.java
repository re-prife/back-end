package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.request.CreateGroupRequestDto;
import kr.hs.mirim.family.entity.User.repository.UserRepository;
import kr.hs.mirim.family.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class GroupService {
    private final UserRepository userRepository;

    public void createGroup(CreateGroupRequestDto requestDto) {
        existsUser(requestDto.getUserId());
    }

    private void existsUser(long userId) {
        if (userRepository.existsByUserId(userId)) {
            throw new DataNotFoundException("User Not Found");
        }
    }
}
