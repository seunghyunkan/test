package com.example.test.entity;

import com.example.test.entity.content.Content;
import com.example.test.entity.content.UserContent;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.util.StringUtils;

@Entity
@Getter
@Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "userId")
    private String id;

    @Comment("이름")
    private String name;

    @Comment("아이디")
    private String loginId;

    @Comment("비밀번호")
    private String loginPw;

    @Comment("닉네임")
    private String nickname;

    @Comment("유저등급")
    @Enumerated(EnumType.STRING)
    private UserAuth userAuth;

    @Comment("프로필")
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserContent userContent;

    public static User create(String name,
                              String loginId,
                              String loginPw,
                              String nickname,
                              Content content) {
        User user = new User();
        user.setName(name);
        user.setLoginId(loginId);
        user.setLoginPw(loginPw);
        user.setNickname(nickname);
        user.setUserContent(UserContent.create(content, user));

        return user;
    }


    public void update(String name,
                              String loginId,
                              String loginPw,
                              String nickname,
                              Content content) {
        if (StringUtils.hasText(name)) {
            this.name = name;
        }
        if (StringUtils.hasText(loginId)) {
            this.loginId = loginId;
        }
        if (StringUtils.hasText(loginPw)) {
            this.loginPw = loginPw;
        }
        if (StringUtils.hasText(nickname)) {
            this.nickname = nickname;
        }
        if (content != null) {
            this.userContent = UserContent.create(content, this);
        }
    }





}