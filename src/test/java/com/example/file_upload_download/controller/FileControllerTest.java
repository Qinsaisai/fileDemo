package com.example.file_upload_download.controller;

import com.sun.xml.internal.ws.api.pipe.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FileControllerTest {
    @Autowired
    private FileController fileController;

    /**
     * 单文件上传测试
     *
     * @throws Exception
     */
    @Test
    public void testUploadFile() throws Exception {
        File file = new File("E:/工作表.xlsx");
        MockMultipartFile multipartFile = new MockMultipartFile("file", "工作表.xlsx", MediaType.APPLICATION_OCTET_STREAM_VALUE, new FileInputStream(file));
        ResponseEntity re = fileController.uploadFile(multipartFile);
        log.info("结果:{}", re);
    }

    /**
     * 多文件上传测试
     *
     * @throws Exception
     */
    @Test
    public void testUploadMultipleFiles() throws Exception {
        File[] files = new File[2];
        files[0] = new File("E:/工作表.xlsx");
        files[1] = new File("E:/新建.xlsx");
        MockMultipartFile[] multipartFiles = new MockMultipartFile[2];
        for (int i = 0; i < multipartFiles.length; i++) {
            multipartFiles[i] = new MockMultipartFile(files[i].toString(), files[i].toString().substring(3), MediaType.APPLICATION_OCTET_STREAM_VALUE, new FileInputStream(files[i]));
        }
        String re = fileController.uploadMultipleFiles(multipartFiles);
        log.info("结果:{}", re);
    }

    /**
     * 文件下载测试
     */
    @Test
    public void testDownloadFile() throws Exception {
        String fileName = "aa.docx";
        MockHttpServletRequest request = new MockHttpServletRequest("fileName", "fileName");
        ResponseEntity<Resource> responseEntity = fileController.downloadFile(fileName, request);
        log.info("结果:{}", responseEntity);
    }


}
