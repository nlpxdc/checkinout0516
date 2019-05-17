package io.cjf.checkinout0516;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("io.cjf.checkinout0516.dao")
public class Checkinout0516Application {

    public static void main(String[] args) {
        SpringApplication.run(Checkinout0516Application.class, args);
    }

}