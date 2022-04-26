package kr.hs.mirim.family.entity;

import lombok.*;

import javax.persistence.*;

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

    @Builder
    public Group(String groupInviteCode, String groupName){
        this.groupInviteCode = groupInviteCode;
        this.groupName = groupName;
    }

    public static Group createGroup(String groupName, String groupInviteCode){
        return new Group(groupInviteCode, groupName);
    }
}
