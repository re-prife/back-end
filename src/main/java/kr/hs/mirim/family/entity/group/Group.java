package kr.hs.mirim.family.entity.group;

import kr.hs.mirim.family.entity.User.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Group {
    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @Column(nullable = false, name = "group_invite_code", columnDefinition = "CHAR(7)")
    private String groupInviteCode;

    @Column(length = 20, nullable = false, name="group_name")
    private String groupName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private List<User> userList = new ArrayList<>();

    @Builder
    public Group(String groupInviteCode, String groupName){
        this.groupInviteCode = groupInviteCode;
        this.groupName = groupName;
    }
}
