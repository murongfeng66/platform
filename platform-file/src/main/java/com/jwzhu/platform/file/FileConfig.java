package com.jwzhu.platform.file;


import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * 文件系统配置
 */
@Configuration
@ConfigurationProperties(prefix = "com.jwzhu.platform.file")
@Validated
public class FileConfig {

    @NotBlank(message = "文件系统根目录不能为空")
    private String rootPath;

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath.endsWith("/") ? rootPath : rootPath + "/";
    }
}
