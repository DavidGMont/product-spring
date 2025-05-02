package me.davidgarmo.soundseeker.product.web.config;

import me.davidgarmo.soundseeker.product.service.IFileUploadService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileUploadConfig implements WebMvcConfigurer {

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Bean
    CommandLineRunner init(IFileUploadService fileUploadService) {
        return args -> fileUploadService.init();
    }
}
