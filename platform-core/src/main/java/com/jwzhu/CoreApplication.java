package com.jwzhu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import com.jwzhu.platform.plugs.jsonescape.base.JsonEscaperScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.jwzhu.platform.core.*.db")
@JsonEscaperScan
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }

}
