package com.example.file_upload_download.utils;

import com.example.file_upload_download.exceptions.NoFileException;
import com.example.file_upload_download.exceptions.UploadFileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author jiangbin
 * @date 2018-12-12 12:59
 * 文件操作工具类
 */
@Component
public class FileUtil {

    /**
     * 文件保存
     * @param fileInputStream
     * @param targetLocation
     * @throws IOException
     */
    public static void save(InputStream fileInputStream, Path targetLocation) throws IOException {
        Files.copy(fileInputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
    }
    /**
     * 单文件上传
     * @param file
     * @return
     * @throws Exception
     */
//    public static String uploadFile(MultipartFile file,String fileStorageDir) throws Exception {
//        //获取文件名
//        String fileName=file.getOriginalFilename();
//        //获取文件存储路径
//        Path fileStorageLocation=Paths.get(fileStorageDir);
//        if (isExcel(file)){
//            try{
//                //初始化创建目录，该方法接收一个Path类型的参数来创建目录
//                Files.createDirectories(fileStorageLocation);
//            } catch (Exception e){
//                throw new UploadFileException("无法创建上传文件的存储目录"+fileName,e);
//            }
//            try{
//                if (fileName.isEmpty()){
//                    throw new UploadFileException("文件为空,上传文件失败"+fileName);
//                }
//                String invalidValue="..";
//                if (fileName.contains(invalidValue)){
//                    throw new UploadFileException("文件名包含无效路径序列，上传文件失败"+fileName);
//                }
//                //目标路径,即存储路径+文件名
//                Path targetLocation=fileStorageLocation.resolve(fileName);
//                //替换已存在的文件
//                Files.copy(file.getInputStream(),targetLocation, StandardCopyOption.REPLACE_EXISTING);
//                return ("单文件上传成功");
//            }catch (Exception e){
//                throw new UploadFileException("无法上传文件"+fileName,e);
//            }
//        }else {
//            throw new UploadFileException("文件类型不符，上传失败"+fileName);
//        }
//    }

    /**
     * 多文件上传
     * @param files
     * @return
     */
//    public static String uploadMultipleFiles(MultipartFile[] files,String fileStorageDir) throws Exception {
//        for(int i=0;i<files.length;i++){
//            try{
//                uploadFile(files[i],fileStorageDir);
//            }catch (Exception e){
//                throw new Exception("第"+(i+1)+"个文件上传失败");
//            }
//        }
//        return ("多文件上传成功");
//    }

    /**
     * 判断上传的文件是否是excel文件
     * @param file
     * @return
     */
    public static boolean isExcel(MultipartFile file){
        //获取文件名
        String fileName=file.getOriginalFilename();
        //获取文件后缀名
        String postfix=fileName.substring(fileName.lastIndexOf(".")+1);
        if (postfix.equals("xlsx")||postfix.equals("xls")){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 将文件作为资源下载
     * @param fileName
     * @return
     */
    public static UrlResource loadFileAsResource(String fileName,String fileStorageDir) {
        try{
            Path filePath=Paths.get(fileStorageDir).resolve(fileName).normalize();
            UrlResource resource=new UrlResource(filePath.toUri());
            if (resource.exists()){
                return resource;
            }else{
                throw new NoFileException("文件没有找到"+fileName);
            }
        } catch (MalformedURLException e) {
            throw new NoFileException("文件没有找到"+fileName,e);
        }
    }


}
