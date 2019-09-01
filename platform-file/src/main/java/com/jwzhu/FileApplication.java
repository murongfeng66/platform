package com.jwzhu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import com.jwzhu.platform.plugs.jsonescape.base.JsonEscaperScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.jwzhu.platform.file.db")
@JsonEscaperScan
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }

}
