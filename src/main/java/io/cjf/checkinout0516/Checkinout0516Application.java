package io.cjf.checkinout0516;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("io.cjf.checkinout0516.dao")
@EnableFeignClients
//@EnableScheduling
public class Checkinout0516Application {

    public static void main(String[] args) {
        SpringApplication.run(Checkinout0516Application.class, args);
    }

}
