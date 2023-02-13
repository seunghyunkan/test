package entity.content;

import entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserContent extends Content{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public static UserContent create(Content content,
                                          User user) {

        UserContent userContent = UserContent.builder()
                .fileNm(content.getFileNm())
                .fileSize(content.getFileSize())
                .fileOrgNm(content.getFileOrgNm())
                .filePath(content.getFilePath())
                .user(user)
                .build();


        return userContent;
    }

}
