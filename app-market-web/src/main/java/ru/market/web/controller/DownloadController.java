package ru.market.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;

@Controller
@RequestMapping(path = "/download")
public class DownloadController {
    @Value("${app.market@download.app.cli.path}")
    private String cliAppFilePath;

    @RequestMapping(path = "/app/cli", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<FileSystemResource> downloadCLIApp(){
        return download(cliAppFilePath);
    }

    private ResponseEntity<FileSystemResource> download(String filePath){
        File file = new File(filePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setContentLength(file.length());
        headers.setContentDispositionFormData("attachment", file.getName());

        return new ResponseEntity<>(new FileSystemResource(file), headers, HttpStatus.OK);
    }
}
