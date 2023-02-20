package com.example.test.entity.content;

import com.example.test.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue(value = ContentType.USER)
public class UserContent extends Content{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public static UserContent create(Content content,
                                          User user) {

        UserContent cont = new UserContent();
        cont.setFilePath(content.getFilePath());
        cont.setFileNm(content.getFileNm());
        cont.setFileOrgNm(content.getFileOrgNm());
        cont.setMimeTp(content.getMimeTp());
        cont.setFileSize(content.getFileSize());
        cont.setUser(user);
        return cont;
    }

}
