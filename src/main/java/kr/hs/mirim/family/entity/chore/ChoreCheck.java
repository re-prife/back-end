package kr.hs.mirim.family.entity.chore;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ChoreCheck {
    BEFORE("인증 요청 전"),
    REQUEST("인증 요청"),
    SUCCESS("수락"),
    FAIL("거절");

    private String check;
}
