package com.jwzhu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.jwzhu.platform.core.*.db")
public class PlatformCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformCoreApplication.class, args);
    }

}
