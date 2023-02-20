package com.example.test.entity;



import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.util.StringUtils;

@Entity
@Getter
@Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
@NoArgsConstructor
public class Comments extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "commentId")
    private String id;

    @Comment("댓글")
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;

    public static Comments create(String memo,
                    User user,
                    Board board) {
        Comments comment = new Comments();
        comment.setMemo(memo);
        comment.setUser(user);
        comment.setBoard(board);

        return comment;
    }

    public void update(String memo) {
        if (StringUtils.hasText(memo)) {
            this.memo = memo;
        }
    }

}
