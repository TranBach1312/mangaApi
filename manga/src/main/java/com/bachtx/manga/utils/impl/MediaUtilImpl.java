package com.bachtx.manga.utils.impl;

import com.bachtx.manga.utils.MediaUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class MediaUtilImpl implements MediaUtil {
    @Value("${file.uploadDir}")
    private String uploadDir;

    @Override
    public String uploadImage(MultipartFile file, String fileType) throws IOException {
        if (!file.getContentType().startsWith(fileType + "/")) {
            throw new IllegalArgumentException("Input invalid!");
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + fileExtension;
        Path folderUpload;
        folderUpload = Paths.get(uploadDir);
        if (!Files.exists(folderUpload)) {
            Files.createDirectories(folderUpload);
        }
        File newFile = new File(folderUpload.toAbsolutePath() + "/" + newFileName);
        file.transferTo(newFile);
        return newFile.getAbsolutePath();
    }

}
