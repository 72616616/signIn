package com.haze;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;

@MapperScan("com.haze.mapper")
@SpringBootApplication
public class SignInApplication {

    public static void main(String[] args) {
        SpringApplication.run(SignInApplication.class, args);
    }

}
