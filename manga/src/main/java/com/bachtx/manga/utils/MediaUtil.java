package com.bachtx.manga.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface MediaUtil {
    String uploadImage(MultipartFile file,String fileType) throws IOException;

}
