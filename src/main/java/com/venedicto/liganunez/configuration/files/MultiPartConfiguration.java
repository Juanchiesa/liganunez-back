package com.venedicto.liganunez.configuration.files;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class MultiPartConfiguration {
	@Value("${tmp.files.dir}")
	private String tmpFilesDirectory;
	@Value("${tmp.files.max.size}")
	private long maxFileSize;
	
	@Bean
    public CommonsMultipartResolver multipartResolver() throws IOException {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        
        multipartResolver.setUploadTempDir(new FileSystemResource(tmpFilesDirectory));
        multipartResolver.setPreserveFilename(true);
        multipartResolver.setMaxUploadSize(maxFileSize);
        
        return multipartResolver;
    }
}
