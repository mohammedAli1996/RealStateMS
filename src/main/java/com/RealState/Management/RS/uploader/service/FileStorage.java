package com.RealState.Management.RS.uploader.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;


public interface FileStorage {
    void store(MultipartFile file, int parentId , String date , String type , String fileName, String section , String amount ) throws IOException;

    Resource loadFile(String filename);

    void deleteAll();

    void init();

    Stream<Path> loadFiles();
}
