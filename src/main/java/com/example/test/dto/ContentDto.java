package com.example.test.dto;

import com.example.test.entity.content.Content;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentDto {
    private String id;

    private String filePath;

    private String fileNm;

    private String fileOrgNm;

    private String mimeTp;

    private Long fileSize;

    public ContentDto(Content content) {
        this.id = content.getId();
        this.filePath = content.getFilePath();
        this.fileNm = content.getFileNm();
        this.fileOrgNm = content.getFileOrgNm();
        this.mimeTp = content.getMimeTp();
        this.fileSize = content.getFileSize();
    }

}
