package entity;



import entity.content.Content;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comments extends Content {

    @Id
    @GeneratedValue
    private String id;

    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;

    public static Comments create(String comments,
                                  User user,
                                  Board board) {

        Comments commentBuilder = Comments.builder()
                .comments(comments)
                .user(user)
                .board(board)
                .build();

        return commentBuilder;
    }
}
