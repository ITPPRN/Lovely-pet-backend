package com.example.lovelypet.exception;

public class FileException extends BaseException {
    public FileException(String code) {
        super("booking." + code);
    }

    //CREATE

    public static FileException fileNull() {
        return new FileException("file.null");
    }

    public static FileException fileMaxSize() {
        return new FileException("max.size");
    }

    public static FileException unsupported() {
        return new FileException("unsupported.filetype");
    }

}
