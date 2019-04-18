package norvina;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NotinoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotinoApplication.class, args);
    }

}
