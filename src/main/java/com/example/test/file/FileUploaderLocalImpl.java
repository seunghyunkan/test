package com.example.test.file;

import com.example.test.entity.content.Content;
import com.example.test.exception.ApiException;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class FileUploaderLocalImpl implements FileUploader{

    private static final String THUMBNAIL_CONTENT_TYPE = "png";

    @Value("${file.upload.home}")
    private String fileUploadHome;

    @Value("${image.thumbnail.ratio}")
    private Double thumbnailRatio;

    @Override
    public Content fileUpload(MultipartFile multipartFile, String contextPath, FileAllowType fileType) {
        String home = fileUploadHome + "/";
        if (StringUtils.isNotBlank(contextPath)) {
            home = home + contextPath + "/";
        }
        return uploader(home, "/" + contextPath + "/", multipartFile, fileType);
    }

    @Override
    public void deleteFile(String fileUrl)  {
        String realPath = fileUploadHome + fileUrl;
        boolean result = new File(realPath).delete();
        if (result) {
            log.info(realPath + "파일 삭제 완료");
        }else {
            log.warn(realPath + "파일 삭제 실패");
        }

    }

    private Content uploader(String home, String filePath, MultipartFile multipartFile, FileAllowType fileAllowType) {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            try {
                String contentType = getContentType(multipartFile, fileAllowType);
                String extension = contentType.substring(contentType.split("/")[0].length() + 1);

                Map<FileType, String> fileNameMap = makeFileName(extension);

                uploadRequest(multipartFile.getInputStream(), home + fileNameMap.get(FileType.ORIGINAL));

                if (contentType.startsWith("image")) {
                    uploadRequest(makeThumbnail(multipartFile, getImageDimension(multipartFile, extension)),
                            home + fileNameMap.get(FileType.THUMBNAIL));

                    return Content.create(filePath,
                                          fileNameMap.get(FileType.ORIGINAL),
                                          multipartFile);
                }
                return Content.create(filePath, fileNameMap.get(FileType.ORIGINAL), multipartFile);
            } catch (IOException io) {
                throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, " 파일 처리에 실패했습니다.");
            }
        }
        return null;
    }

    private void uploadRequest(InputStream inputStream, String targetPath) {
        try {
            FileUtils.copyInputStreamToFile(inputStream, new File(targetPath));
        } catch (IOException ioException) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 저장 실패");
        }
    }

    private void uploadRequest(byte[] content, String targetPath) {
        try {
            FileUtils.writeByteArrayToFile(new File(targetPath), content);
        } catch (IOException ioException) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 저장 실패");
        }
    }

    private void sizeValidate(MultipartFile multipartFile, Long maxSize) {
        if (maxSize == null || maxSize == 0) {
            return;
        }
        if (multipartFile.getSize() > maxSize) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    multipartFile.getOriginalFilename() + "의 파일 사이즈가 " + maxSize / 10e6 + "Mb를 초과합니다.");
        }
    }

    private byte[] makeThumbnail(MultipartFile multipartFile, Dimension originalDimension) throws IOException {
        int thumbnailWidth = (int) (originalDimension.getWidth() * thumbnailRatio);
        int thumbnailHeight = (int) (originalDimension.getHeight() * thumbnailRatio);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); InputStream is = multipartFile.getInputStream()) {
            Thumbnails.of(is)
                    .size(thumbnailWidth, thumbnailHeight)
                    .outputFormat(THUMBNAIL_CONTENT_TYPE)
                    .outputQuality(0.1f)
                    .toOutputStream(baos);
            return baos.toByteArray();
        }
    }

    private String getContentType(MultipartFile multipartFile, FileAllowType fileType) throws IOException {
        String contentType = new Tika().detect(multipartFile.getInputStream());
        switch (fileType) {
            case ONLY_IMAGE -> {
                if (!contentType.contains("image")) {
                    throw new ApiException(HttpStatus.BAD_REQUEST, "image 파일만 업로드 가능합니다.");
                }
            }
            case ONLY_VIDEO -> {
                if (!contentType.contains("video")) {
                    throw new ApiException(HttpStatus.BAD_REQUEST, "동영상 파일만 업로드 가능합니다.");
                }
            }
            case IMAGE_OR_VIDEO -> {
                if (!contentType.contains("image") && !contentType.contains("video")) {
                    throw new ApiException(HttpStatus.BAD_REQUEST, "이미지 또는 동영상 파일만 업로드 가능합니다.");
                }
            }
        }
        return multipartFile.getContentType();
    }

    private Map<FileType, String> makeFileName(String extension) {
        UUID uuid = UUID.randomUUID();
        Map<FileType, String> nameMap = new HashMap<>();
        nameMap.put(FileType.ORIGINAL, uuid + "." + extension);
        nameMap.put(FileType.THUMBNAIL, uuid + "_thumb." + THUMBNAIL_CONTENT_TYPE);
        return nameMap;
    }

    private Dimension getImageDimension(MultipartFile multipartFile, String extension) throws IOException {
        return new Dimension(3000, 3000);
    }

    private enum FileType {
        ORIGINAL,
        THUMBNAIL
    }
}
