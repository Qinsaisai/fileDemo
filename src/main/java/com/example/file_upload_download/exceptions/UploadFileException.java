package com.example.file_upload_download.exceptions;

public class UploadFileException extends Exception {
    public UploadFileException(String message){
        super(message);
    }
    public UploadFileException(String message,Throwable cause){
        super(message,cause);
    }
}