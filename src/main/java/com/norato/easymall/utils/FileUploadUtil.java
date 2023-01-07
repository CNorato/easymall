package com.norato.easymall.utils;

import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.UUID;

public class FileUploadUtil {

    public static String uploadFile(String path, MultipartFile file) {
        // 1.检查文件名称
        // 获取图名称，后缀名称
        String filename = file.getOriginalFilename();
        if (!StringUtils.hasText(filename)) {
            throw new RuntimeException("文件名为空");
        }

        // 截取后缀substring split (".png" ".jpg")
        String extName = filename.substring(filename.lastIndexOf("."));
        if (!(extName.equalsIgnoreCase(".jpg") || extName.equalsIgnoreCase(".png")
                || extName.equalsIgnoreCase(".gif"))) {// 图片后缀不合法
            throw new RuntimeException("图片后缀不合法!");
        }

        // 建立唯一的文件名
        filename = UUID.randomUUID() + extName;

        // 判断木马(java的类判断是否是图片属性，也可以引入第三方jar包判断)
        try {
            BufferedImage bufImage = ImageIO.read(file.getInputStream());
            bufImage.getHeight();
            bufImage.getWidth();
        } catch (Exception e) {
            throw new RuntimeException("图片内容不合法!");
        }

        // 2.获取运行路径
        File rootPath;
        try {
            rootPath = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("获取文件路径失败");
        }
        if (!rootPath.exists()) rootPath = new File("");

        File uploadDir = new File(rootPath.getAbsolutePath(), path);
        if (!uploadDir.exists()) {
            if (!uploadDir.mkdirs()) {
                throw new RuntimeException("创建rootPath失败");
            }
        }

        // 3.创建upload开始的一个路径
        // 生成多级路径
        StringBuilder imgurl = new StringBuilder("/upload");
        // /a/2/e/a/2/3/j/p
        for (int i = 0; i < 8; i++) {
            imgurl.append("/").append(Integer.toHexString(new Random().nextInt(16)));
        }
        File realPath = new File(uploadDir, imgurl.toString());
        if (!realPath.exists()) {
            if (!realPath.mkdirs()) {
                throw new RuntimeException("创建realPath失败");
            }
        }

        File desc = new File(realPath, filename);
        // 4.上传文件
        try {
            file.transferTo(desc);
        } catch (Exception e) {
            throw new RuntimeException("文件上传失败");
        }

        // 拼接图片存入数据库的路径
        return imgurl + "/" + filename;
    }
}