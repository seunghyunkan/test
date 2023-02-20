package com.example.test.dto;

import com.example.test.entity.User;
import com.example.test.entity.UserAuth;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class UserDto {

    @Getter
    public static class Info {
        private final String id;
        private final String name;

        private final String loginId;

        private final String loginPw;

        private final String nickname;

        private final UserAuth userAuth;

        private final ContentDto userContent;

        public Info(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.loginId = user.getLoginId();
            this.loginPw = user.getLoginPw();
            this.nickname = user.getNickname();
            this.userAuth = user.getUserAuth();
            this.userContent = new ContentDto(user.getUserContent());
        }
    }

    @Getter
    @Setter
    public static class Save {
        @NotBlank
        private String name;

        @NotBlank
        private String loginId;

        @NotBlank
        private String loginPw;

        @NotBlank
        private String nickname;

        private MultipartFile userContent;
    }

    @Getter
    @Setter
    public static class Update {
        @NotBlank
        private String id;
        private String name;

        private String loginId;

        private String loginPw;

        private String nickname;

        private MultipartFile userContent;

    }

    @Getter
    @Setter
    public static class Delete {
        private List<String> ids;
    }
}
