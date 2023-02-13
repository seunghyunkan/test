package entity.content;


import entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public class Content extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String filePath;

    private String fileNm;

    private String fileOrgNm;

    private String mimeTp;

    private Long fileSize;

    public static Content create(String filePath,
                                 String fileNm,
                                 MultipartFile multipartFile) {
        Content cont = Content.builder()
                .filePath(filePath)
                .fileNm(fileNm)
                .fileOrgNm(multipartFile.getOriginalFilename())
                .mimeTp(multipartFile.getContentType())
                .fileSize(multipartFile.getSize())
                .build();

        return cont;
    }
}
