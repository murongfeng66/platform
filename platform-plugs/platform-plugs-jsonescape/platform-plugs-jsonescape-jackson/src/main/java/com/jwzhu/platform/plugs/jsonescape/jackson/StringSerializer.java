package com.jwzhu.platform.plugs.jsonescape.jackson;

import java.io.IOException;
import java.lang.reflect.Field;

import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.common.util.StringUtil;
import com.jwzhu.platform.plugs.jsonescape.bind.JsonEscapeInterface;
import com.jwzhu.platform.plugs.jsonescape.bind.StringEscape;

public class StringSerializer extends JacksonSerializer<String> {

    StringSerializer(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value);
        try {
            String fieldName = gen.getOutputContext().getCurrentName();
            if (StringUtil.isEmpty(fieldName)) {
                return;
            }

            Object object = gen.getOutputContext().getCurrentValue();
            Field field = getField(object, fieldName);
            if (field == null) {
                return;
            }

            StringEscape escape = field.getAnnotation(StringEscape.class);
            if (escape == null) {
                return;
            }

            JsonEscapeInterface<String> escaper = applicationContext.getBean(escape.value());
            Object messages = escaper.getMessage(value);

            writeEscape(gen, fieldName, escape.targetNames(), messages);
        } catch (SystemException | BusinessException e) {
            logger.warn("序列化错误：{}", e.getMessage());
        } catch (Exception e) {
            logger.error("序列化错误", e);
        }
    }

}
