package ru.market.web.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
public class UploadFileController {
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public void uploadFile(@RequestParam("file") MultipartFile multipartFile){
        try {
            InputStream inputStream = multipartFile.getInputStream();

            byte[] buffer = new byte[256];

            while (inputStream.read(buffer) != -1){
                System.out.println(new String(buffer, StandardCharsets.UTF_8));
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
