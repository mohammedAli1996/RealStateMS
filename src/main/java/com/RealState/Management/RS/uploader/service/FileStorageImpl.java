package com.RealState.Management.RS.uploader.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.RealState.Management.RS.Property.Attachmenets.AttachmentRepository;
import com.RealState.Management.RS.Property.Attachmenets.AttachmentService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;



@Service
public class FileStorageImpl implements FileStorage {

	@Autowired
	AttachmentRepository attachmentRepository ; 
	
	@Autowired
    private AttachmentService attachmentService ;
	
	@Autowired
	private FilesSequenceRepository filesSequenceRepository ; 
	
	
	
    private final Path rootLocation = Paths.get("filestorage");
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    
    
    @Override
    public void store(MultipartFile file,int parentId , String date , String type , String fileName ,String section , String amount) throws IOException {
    	int id = getSequence();
        try {
            Files.copy(file.getInputStream(), this.rootLocation.resolve(id+"_"+file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        } 
        String path = id+"_"+file.getOriginalFilename() ; 
        attachmentService.saveAttachment(fileName,path , parentId, type, date,section , amount);
    }

    @Override
    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error! -> message = " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }

    @Override
    public Stream<Path> loadFiles() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new RuntimeException("\"Failed to read stored file");
        }
    }
    
    
    private static int currentSeq  ; 
    
//    @PostConstruct
    private void setSequence() {
    	if(filesSequenceRepository.findAll().size() == 0 ) {
    		FilesSequence seq = new FilesSequence();
    		seq.setSeq(1);
    		currentSeq = 1 ;
    		filesSequenceRepository.save(seq);
    	}else {
    		currentSeq = filesSequenceRepository.findAll().get(0).getSeq();
    	}
    }
    
    private int getSequence() {
    	setSequence();
    		FilesSequence seq = filesSequenceRepository.findAll().get(0);
    		currentSeq ++; 
    		seq.setSeq(currentSeq);
    		return filesSequenceRepository.save(seq).getSeq();
    }
    
}