package com.example.file_upload_download.controller;

import com.sun.xml.internal.ws.api.pipe.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class FileControllerTest {
    @Autowired
    private FileController fileController;
    @Autowired
    private MockMvc mvc;

    /**
     * 单文件上传测试
     *
     * @throws Exception
     */
    @Test
    public void testUploadFile() throws Exception{
        File file = new File("E:/工作表.xlsx");
        MockMultipartFile multipartFile = new MockMultipartFile("file", "工作表.xlsx", MediaType.APPLICATION_OCTET_STREAM_VALUE, new FileInputStream(file));
        mvc.perform(MockMvcRequestBuilders.multipart("/api/uploadFile")
                .file(multipartFile))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 多文件上传测试(不正确，请知道怎么写这个多文件上传测试的人儿告诉我以下，谢谢啦)
     *
     * @throws Exception
     */
    @Test
    public void uploadMultipleFilesTest() throws Exception {
        File file1 = new File("E:/工作表.xlsx");
        File file2 = new File("E:/新建.xlsx");
        MockMultipartFile[] mockMultipartFiles=new MockMultipartFile[2];
        mockMultipartFiles[0]=new MockMultipartFile("file1", "工作表.xlsx", MediaType.APPLICATION_OCTET_STREAM_VALUE, new FileInputStream(file1));
        mockMultipartFiles[1]=new MockMultipartFile("file2", "新建.xlsx", MediaType.APPLICATION_OCTET_STREAM_VALUE, new FileInputStream(file2));
        for (int i=0;i<mockMultipartFiles.length;i++){
            mvc.perform(MockMvcRequestBuilders.multipart("/api/uploadMultipleFiles")
                    .file(mockMultipartFiles[i]))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        }
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
