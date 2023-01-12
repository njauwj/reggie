package com.atwj.reggie.controller;

import com.atwj.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * @author: wj
 * @create_time: 2022/11/12 19:16
 * @explain: 文件上传和下载
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    /**
     * Content-Disposition: form-data; name="file"; filename="1.jpg"
     * Content-Type: image/jpeg
     **/
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {//参数名不能乱写，必须和前端保持一致
        if (file.isEmpty()) {
            return R.error("文件上传失败");
        }
        //获取文件原是名
        String originalFilename = file.getOriginalFilename();
        //得到文件名后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 使用uuid生成随机数,防止文件名重名
        String filename = UUID.randomUUID().toString() + suffix;

        //创建一个目录对象
        File dir = new File(basePath);
        //判断目录是否存在
        if (!dir.exists()) {
            dir.mkdir();
        }

        try {
            file.transferTo(new File(basePath + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.success(filename);

    }


    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            fileInputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
