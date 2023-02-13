package entity;

import entity.content.BoardContent;
import entity.content.Content;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board extends Content {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Comment("제목")
    private String title;

    @Comment("본문_내용")
    private String post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board")
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comments> comments = new ArrayList<>();


    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardContent> boardContentList = new ArrayList<>();

    public static Board create(String title,
                               String post,
                               User user,
                               List<Comments> comments,
                               List<Content> contentList) {
        Board board = new Board();
        List<BoardContent> boardContents = new ArrayList<>();
        for (Content content : contentList) {
            boardContents.add(BoardContent.builder().board(null).build()) ;
        }

        Board boardBuilder = Board.builder()
                .title(title)
                .post(post)
                .user(user)
                .comments(comments)
                .boardContentList(boardContents)
                .build();

        return boardBuilder;
    }


}
