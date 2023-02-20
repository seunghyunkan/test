package com.example.test.file;

import com.example.test.entity.content.Content;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploader {

    Content fileUpload(MultipartFile multipartFile, String contextPath, FileAllowType fileType);

    void deleteFile(String fileUrl) throws IOException;



}
