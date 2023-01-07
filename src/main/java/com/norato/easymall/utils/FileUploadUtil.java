package com.norato.easymall.utils;

import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;

public class FileUploadUtil {
    public static void uploadFile(String path, MultipartFile file) throws FileNotFoundException {
        File rootPath = new File(ResourceUtils.getURL("classpath:").getPath());
        if (!rootPath.exists()) rootPath = new File("");

        File uploadDir = new File(rootPath.getAbsolutePath(), path);
        if (!uploadDir.exists()) {
            if (!uploadDir.mkdirs()) {
                throw new RuntimeException("创建文件夹失败");
            }
        }

        String filename = file.getOriginalFilename();
        if (!StringUtils.hasText(filename)) {
            throw new RuntimeException("文件名为空");
        }
        File desc = new File(uploadDir, filename);

        try {
            file.transferTo(desc);
        } catch (Exception e) {
            throw new RuntimeException("文件上传失败");
        }
    }
}