package kr.hs.mirim.family.entity.user;

import kr.hs.mirim.family.entity.group.Group;
import kr.hs.mirim.family.entity.chore.Chore;
import kr.hs.mirim.family.entity.quest.Quest;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "user_tb")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(length = 20, nullable = false, name = "user_name")
    private String userName;

    @Column(length = 60, nullable = false, name = "user_password")
    private String userPassword;

    @Column(length = 50, nullable = false, name = "user_email", unique = true)
    private String userEmail;

    @Column(length = 10, nullable = false, name = "user_image_name")
    private String userImageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Chore> choreList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Quest> questList = new ArrayList<>();

    @Builder
    public User(String userName, String userPassword, String userEmail, String userImageName) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userImageName = userImageName;
    }
}