package kr.hs.mirim.family.entity.chore;

import kr.hs.mirim.family.entity.BaseEntity;
import kr.hs.mirim.family.entity.group.Group;
import kr.hs.mirim.family.entity.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chore_tb")
@Getter
public class Chore extends BaseEntity {

    @Id
    @Column(name = "chore_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long choreId;

    @Column(name = "chore_title", length = 20, nullable = false)
    private String choreTitle;

    @Column(name = "chore_check", nullable = false, columnDefinition = "CHAR(7)")
    @Enumerated(EnumType.STRING)
    private ChoreCheck choreCheck;

    @Column(name = "chore_date", nullable = false)
    private LocalDate choreDate;

    @Column(name = "chore_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private ChoreCategory choreCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chore_user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Builder
    public Chore(String choreTitle, ChoreCheck choreCheck, ChoreCategory choreCategory, LocalDate choreDate,User user, Group group){
        this.choreTitle = choreTitle;
        this.choreCheck = choreCheck;
        this.choreCategory = choreCategory;
        this.choreDate = choreDate;
        this.user = user;
        this.group = group;
    }
}
