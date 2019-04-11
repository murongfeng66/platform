package com.jwzhu.platform.plugs.jsonEscape.jackson;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Configuration
public class JacksonSerializerRegister {

    @Bean
    public ObjectMapper objectMapper(ApplicationContext applicationContext){
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Integer.class, new IntegerSerializer(applicationContext));
        module.addSerializer(Short.class, new ShortSerializer(applicationContext));
        objectMapper.registerModule(module);
        return objectMapper;
    }

}
