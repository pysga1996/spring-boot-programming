package com.lambda.uploaddownload;

import com.lambda.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.lambda")
@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class UploadDownloadApplication {

  public static void main(String[] args) {
    SpringApplication.run(UploadDownloadApplication.class, args);
  }

}
