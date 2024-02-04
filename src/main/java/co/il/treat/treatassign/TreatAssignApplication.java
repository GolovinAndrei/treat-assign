package co.il.treat.treatassign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class TreatAssignApplication {

    public static void main(String[] args) {
        SpringApplication.run(TreatAssignApplication.class, args);
    }


}
