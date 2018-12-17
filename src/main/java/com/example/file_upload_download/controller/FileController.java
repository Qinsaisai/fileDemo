package com.example.file_upload_download.controller;

import com.example.file_upload_download.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class FileController {

    @Value("${file.upload-dir}")
    private String fileUploadDir;

    /**
     * 单文件上传
     * @param multipartFile
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadFile")
    public ResponseEntity uploadFile(@RequestPart("file") MultipartFile multipartFile) throws Exception {
        String fileName = multipartFile.getOriginalFilename();
        Path path=  Paths.get(fileUploadDir).resolve(fileName);
        FileUtil.save(multipartFile.getInputStream(), path);
        return ResponseEntity.ok().build();
    }

    /**
     * 多文件上传
     * @param files
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadMultipleFiles")
    public ResponseEntity uploadMultipleFiles(@RequestPart("files") MultipartFile[] files) throws Exception {
        for (int i=0;i<files.length;i++){
            String fileName=files[i].getOriginalFilename();
            Path path=Paths.get(fileUploadDir).resolve(fileName);
            FileUtil.save(files[i].getInputStream(),path);
        }
        return ResponseEntity.ok().build();
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
        UrlResource resource= FileUtil.loadFileAsResource(fileName,fileUploadDir);
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
