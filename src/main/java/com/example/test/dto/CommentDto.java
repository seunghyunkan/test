package com.example.test.dto;

import com.example.test.entity.Board;
import com.example.test.entity.Comments;
import com.example.test.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class CommentDto {

    @Getter
    public static class Info {

        private final String id;
        private final String memo;

        private final User user;

        private final Board board;

        public Info(Comments comments) {
            this.id = comments.getId();
            this.memo = comments.getMemo();
            this.user = comments.getUser();
            this.board = comments.getBoard();
        }
    }

    @Getter
    @Setter
    public static class Save {
        @NotBlank
        private String comments;

        private User user;
    }

    @Getter
    @Setter
    public static class Update{
        @NotBlank
        private String comments;
    }

    @Getter
    @Setter
    public static class Delete {
        private List<String> ids;
    }
}
