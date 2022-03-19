package com.RealState.Management.RS;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.RealState.Management.RS.uploader.service.FileStorage;

@SpringBootApplication
public class RsApplication implements CommandLineRunner{

	 @Resource
	 FileStorage fileStorage;                  
	 
	public static void main(String[] args) {    
		SpringApplication.run(RsApplication.class, args);
	}  
          	  
	       
	@Override      
    public void run(String... args) {    
//        fileStorage.deleteAll();    
//        fileStorage.init();  
    }
}   
                             