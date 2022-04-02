package chae4ek.transgura.util;

import java.lang.reflect.Field;

public class ReflectUtils {

  public static Field getField(final Class<?> clazz, final String fieldName) {
    try {
      final Field field = clazz.getDeclaredField(fieldName);
      field.setAccessible(true);
      return field;
    } catch (final NoSuchFieldException e) {
      throw new RuntimeException("The field is not found", e);
    }
  }

  public static void setFieldValue(final Object obj, final Field field, final Object value) {
    try {
      field.set(obj, value);
    } catch (final IllegalAccessException e) {
      throw new RuntimeException("Error setting the field's value", e);
    }
  }

  public static void setFieldValue(
      final Object obj, final Class<?> clazz, final String fieldName, final Object value) {
    setFieldValue(obj, getField(clazz, fieldName), value);
  }

  @SuppressWarnings("unchecked")
  public static <T> T getFieldValue(
      final Class<?> clazz, final Object obj, final String fieldName) {
    try {
      return (T) getField(clazz, fieldName).get(obj);
    } catch (final IllegalAccessException e) {
      throw new RuntimeException("Error getting the field's value", e);
    }
  }
}
