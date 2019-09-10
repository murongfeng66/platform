package com.jwzhu.platform.plugs.jsonescape.jackson;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.jwzhu.platform.common.exception.SystemException;
import com.jwzhu.platform.common.util.ReflectUtil;

public abstract class JacksonSerializer<T> extends JsonSerializer<T> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    ApplicationContext applicationContext;

    JacksonSerializer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    Field getField(Object object, String fieldName) {
        Field field = ReflectUtil.getField(object, fieldName);
        if (field == null) {
            List<Field> fields = ReflectUtil.getFields(object);
            for (Field field1 : fields) {
                JsonProperty jsonProperty = field1.getAnnotation(JsonProperty.class);
                if (jsonProperty != null && jsonProperty.value().equals(fieldName)) {
                    field = field1;
                }
            }
        }
        return field;
    }

    void writeEscape(JsonGenerator gen, String fieldName, String[] targetNames, Object messages) throws IOException {
        if (messages == null) {
            return;
        }

        if (targetNames.length == 0) {
            targetNames = new String[]{fieldName + "Text"};
        }

        if (messages instanceof String) {
            gen.writeObjectField(targetNames[0], messages);
        } else if (messages instanceof String[]) {
            String[] messages1 = (String[]) messages;
            for (int i = 0; i < targetNames.length; i++) {
                if (i < messages1.length) {
                    gen.writeObjectField(targetNames[i], messages1[i]);
                } else {
                    gen.writeObjectField(targetNames[i], null);
                }
            }
        } else {
            throw new SystemException("序列化器返回值错误");
        }
    }
}
