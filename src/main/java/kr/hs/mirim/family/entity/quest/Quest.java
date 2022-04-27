package kr.hs.mirim.family.entity.quest;

import kr.hs.mirim.family.entity.Group.Group;
import kr.hs.mirim.family.entity.User.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "quest_tb")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Quest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quest_id")
    private Long questId;

    @Column(length = 20, nullable = false, name = "quest_title")
    private String questTitle;

    @Column(length = 100, nullable = false, name = "quest_content")
    private String questContent;

    @Column(name = "accept_user_id", nullable = false)
    private int acceptUserId;


    @Column(name = "complete_check", nullable = false)
    private boolean completeCheck;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Builder
    public Quest(String questTitle, String questContent, int acceptUserId, boolean completeCheck){
        this.questTitle = questTitle;
        this.questContent = questContent;
        this.acceptUserId = acceptUserId;
        this.completeCheck = completeCheck;
    }
}