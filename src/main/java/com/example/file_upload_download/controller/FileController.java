package com.example.file_upload_download.controller;

import com.example.file_upload_download.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api")
@Slf4j
public class FileController {

    @Autowired
    private FileUtil fileUtil;

    /**
     * 单文件上传
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadFile")
    public ResponseEntity uploadFile(@RequestPart("file") MultipartFile file) throws Exception {
        log.error(file.getOriginalFilename());
        log.error(String.valueOf(file.getSize()));
        fileUtil.uploadFile(file);
        return ResponseEntity.ok("ok");
    }

    /**
     * 多文件上传
     * @param files
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadMultipleFiles")
    public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) throws Exception {
        return fileUtil.uploadMultipleFiles(files);
    }

    /**
     * 文件下载（通过封装ResponseEntity，将文件流写入body中。注意：文件的格式需要根据具体文件的类型来设置，一般默认为application/octet-stream。文件头中设置缓存以及文件的名字）
     * @param fileName
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/downloadFile/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws Exception {
        UrlResource resource=fileUtil.loadFileAsResource(fileName);
        String contentType=null;
        contentType=request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        if(contentType==null){
            contentType="application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\""+ URLEncoder.encode(resource.getFilename(), "utf-8")+"\"")
                .body(resource);
    }
}
