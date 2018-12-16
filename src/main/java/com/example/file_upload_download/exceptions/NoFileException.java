package com.example.file_upload_download.exceptions;

public class NoFileException extends RuntimeException {
    public NoFileException(String message) {
        super(message);
    }

    public NoFileException(String message, Throwable cause) {
        super(message, cause);
    }
}