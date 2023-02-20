package com.example.test.dto;

import com.example.test.entity.Board;
import com.example.test.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class BoardDto {

    @Getter
    @Setter
    public static class Search {
        private String title;

        private User user;
    }


    @Getter
    public static class Info {

        private final String id;
        private final String title;
        private final String post;
        private final User user;
        private final List<CommentDto.Info> commentList;
        private final List<ContentDto> boardContent;

        public Info(Board board) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.post = board.getPost();
            this.user = board.getUser();
            this.commentList = board.getComments().stream()
                    .map(CommentDto.Info::new)
                    .toList();
            this.boardContent = board.getBoardContentList().stream()
                    .map(ContentDto::new)
                    .toList();
        }
    }

    @Getter
    @Setter
    public static class Save {
        @NotBlank
        private String title;

        @NotBlank
        private String post;


        private User user;

        private List<ContentDto> boardContent;
    }

    @Getter
    @Setter
    public static class Update {
        @NotBlank
        private String title;

        @NotBlank
        private String post;

        private List<ContentDto> boardContent;
    }

    @Getter
    @Setter
    public static class Delete {
        private List<String> ids;
    }


}
