package vn.vissoft.ems.core;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EncryptablePropertySource(name = "EncryptedProperties", value = "classpath:application.properties")
public class EMSApplication extends SpringBootServletInitializer {

  public static ApplicationContext ctx;

  @Autowired
  private void setApplicationContext(ApplicationContext applicationContext) {
    ctx = applicationContext;
  }

  public static void main(String[] args) {
    SpringApplication.run(EMSApplication.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(EMSApplication.class);
  }

}
