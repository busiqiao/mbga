package cn.waitfor.mbga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"cn.waitfor.mbga", "org.jooq.generated"})
public class ApplicationService {

  public static void main(String[] args) {
    SpringApplication.run(ApplicationService.class, args);
  }
}
