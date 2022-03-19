package com.RealState.Management.RS.uploader.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.RealState.Management.RS.uploader.service.FileStorage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class UploadFileController {

    @Autowired
    FileStorage fileStorage;

    

      
    @PostMapping("/upload/{contractId}/{fileName}/{date}/{fileType}/{section}") 
    public String uploadMultipartFile(@RequestParam("files") MultipartFile[] files, Model model 
    		, @PathVariable int contractId , @PathVariable String fileName 
    		, @PathVariable String date , @PathVariable String fileType , @PathVariable String section) {
        List<String> fileNames = null;
        try {
            fileNames = Arrays.asList(files)  
                    .stream()     
                    .map(file -> { 
                        try { 
							fileStorage.store(file,contractId,date,fileType,fileName,section);
						} catch (IOException e) {

							e.printStackTrace();
						}
                        return file.getOriginalFilename();
                    })
                    .collect(Collectors.toList());

            model.addAttribute("message", "Files uploaded successfully!");
            model.addAttribute("files", fileNames); 
        } catch (Exception e) {
            model.addAttribute("message", "Fail!");
            model.addAttribute("files", fileNames);
        }
         return "pp"; 
    }

}