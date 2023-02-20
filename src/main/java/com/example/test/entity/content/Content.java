package com.example.test.entity.content;


import com.example.test.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Getter
@Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public class Content extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Comment("파일_경로")
    private String filePath;

    @Comment("저장된_파일_이름")
    private String fileNm;

    @Comment("파일_이름")
    private String fileOrgNm;

    @Comment("파일_타입")
    private String mimeTp;

    @Comment("파일_크기")
    private Long fileSize;

    public static Content create(String filePath,
                                 String fileNm,
                                 MultipartFile multipartFile) {
        Content content = new Content();
        content.setFilePath(filePath);
        content.setFileNm(fileNm);
        content.setFileOrgNm(multipartFile.getOriginalFilename());
        content.setMimeTp(multipartFile.getContentType());
        content.setFileSize(multipartFile.getSize());
        return content;
    }
}
