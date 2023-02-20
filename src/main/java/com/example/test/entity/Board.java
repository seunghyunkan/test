package com.example.test.entity;

import com.example.test.entity.content.BoardContent;
import com.example.test.entity.content.Content;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "boardId")
    private String id;
    @Comment("제목")
    private String title;

    @Comment("본문_내용")
    private String post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
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
        List<BoardContent> boardContent = new ArrayList<>();
        for (Content content : contentList) {
            boardContent.add(BoardContent.create(content, board));
        }
        board.setTitle(title);
        board.setPost(post);
        board.setUser(user);
        board.setComments(comments);
        board.setBoardContentList(boardContent);

        return board;
    }

    public void update(String title,
                       String post,
                       List<Content> contentList) {
        List<BoardContent> boardContents = new ArrayList<>();

        if (StringUtils.hasText(title)) {
            this.title = title;
        }
        if (StringUtils.hasText(post)) {
            this.post = post;
        }
        if (contentList != null) {

        }
    }
}
