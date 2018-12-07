package com.jwzhu.platform.common.reflect;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jwzhu.platform.common.exception.SystemException;


/**
 * 反射工具
 */
public class ReflectUtils {

	/**
	 * 获取指定对象里面的指定属性
	 * @param obj 目标对象
	 * @param fieldName 目标属性
	 * @return 目标字段
	 */
	public static Field getField(Object obj, String fieldName) {
		List<Field> fields = getFields(obj);
		for (Field field : fields) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		return null;
	}

	/**
	 * 获取对象的所有属性，包含除Object的所有父类
	 */
	public static List<Field> getFields(Object obj) {
		List<Field> fields = new ArrayList<>();
		for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
			fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		}
		return fields;
	}

	/**
	 * 获取指定对象的指定属性值
	 * @param object 目标对象
	 * @param fieldName 目标属性
	 * @return 目标属性的值
	 */
	public static Object getFieldValue(Object object, String fieldName) {
		Object result = null;
		Field field = getField(object, fieldName);
		if (field != null) {
			field.setAccessible(true);
			try {
				result = field.get(object);
            } catch (Exception e) {
                throw new SystemException(e);
            } finally {
                field.setAccessible(false);
            }
		}
		return result;
	}

	/**
	 * 设置指定对象的指定属性值
	 * @param obj 目标对象
	 * @param fieldName 目标属性
	  * @param fieldValue 目标值
	 */
	public static void setFieldValue(Object obj, String fieldName, Object fieldValue) {
		Field field = getField(obj, fieldName);
		if (field != null) {
			try {
				field.setAccessible(true);
				field.set(obj, fieldValue);
            } catch (Exception e) {
                throw new SystemException(e);
            } finally {
                field.setAccessible(false);
            }
		}
	}

	/**
	 * 将对象转为Map
	 */
	public static Map<String, String> toMap(Object obj) {
		Map<String, String> map = new HashMap<>();
		List<Field> fileds = getFields(obj);
		try {
			for (Field field : fileds) {
				field.setAccessible(true);
				Object value = field.get(obj);
				if (value != null) {
					map.put(field.getName(), value.toString());
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new SystemException(e);
		}
		return map;
	}

	/**
	 * 将Map转为对象
	 */
	public static <T> T toObject(T t, Map<String, Object> map) {
		List<Field> fileds = getFields(t);
		try {
			for (Field field : fileds) {
				String key = field.getName();
				field.setAccessible(true);
				if (map.containsKey(key)) {
					field.set(t, map.get(key));
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new SystemException(e);
		}

		return t;
	}

	private ReflectUtils() {}
}
