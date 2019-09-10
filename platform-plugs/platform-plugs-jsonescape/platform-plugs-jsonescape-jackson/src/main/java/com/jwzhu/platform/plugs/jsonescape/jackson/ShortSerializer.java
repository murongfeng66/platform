package com.jwzhu.platform.plugs.jsonescape.jackson;

import java.io.IOException;
import java.lang.reflect.Field;

import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jwzhu.platform.common.exception.BusinessException;
import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.common.util.StringUtil;
import com.jwzhu.platform.plugs.jsonescape.bind.EnumEscape;
import com.jwzhu.platform.plugs.jsonescape.bind.JsonEscapeInterface;
import com.jwzhu.platform.plugs.jsonescape.bind.ShortEscape;

public class ShortSerializer extends JacksonSerializer<Short> {

    ShortSerializer(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void serialize(Short value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeNumber(value);
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

            String[] targetNames = null;
            Object messages = null;

            ShortEscape shortEscape = field.getAnnotation(ShortEscape.class);
            if (shortEscape != null) {
                JsonEscapeInterface<Short> escaper = applicationContext.getBean(shortEscape.value());
                messages = escaper.getMessage(value);
                targetNames = shortEscape.targetNames();
            }

            EnumEscape enumEscape = field.getAnnotation(EnumEscape.class);
            if (enumEscape != null) {
                JsonEscapeInterface<Short> escaper = (JsonEscapeInterface<Short>) enumEscape.value().getEnumConstants()[0];
                messages = escaper.getMessage(value);
                targetNames = enumEscape.targetNames();
            }

            writeEscape(gen, fieldName, targetNames, messages);
        } catch (SystemException | BusinessException e) {
            logger.warn("序列化错误：{}", e.getMessage());
        } catch (Exception e) {
            logger.error("序列化错误", e);
        }
    }

}
