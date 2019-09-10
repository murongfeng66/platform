package com.jwzhu.platform.plugs.jsonescape.jackson;

import java.io.IOException;
import java.lang.reflect.Field;

import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.common.util.StringUtil;
import com.jwzhu.platform.plugs.jsonescape.bind.BooleanEscape;
import com.jwzhu.platform.plugs.jsonescape.bind.JsonEscapeInterface;

public class BooleanSerializer extends JacksonSerializer<Boolean> {

    BooleanSerializer(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    public void serialize(Boolean value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeBoolean(value);
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

            BooleanEscape escape = field.getAnnotation(BooleanEscape.class);
            if (escape == null) {
                return;
            }

            JsonEscapeInterface<Boolean> escaper = applicationContext.getBean(escape.value());
            Object messages = escaper.getMessage(value);

            writeEscape(gen, fieldName, escape.targetNames(), messages);
        } catch (SystemException | BusinessException e) {
            logger.warn("序列化错误：{}", e.getMessage());
        } catch (Exception e) {
            logger.error("序列化错误", e);
        }
    }

}
