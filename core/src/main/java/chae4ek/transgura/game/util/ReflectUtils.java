package chae4ek.transgura.game.util;

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

  public static long getLongValue(final Field field, final Object object) {
    try {
      return field.getLong(object);
    } catch (final IllegalAccessException e) {
      throw new RuntimeException("Error getting the field's value", e);
    }
  }

  public static int getIntValue(final Field field, final Object object) {
    try {
      return field.getInt(object);
    } catch (final IllegalAccessException e) {
      throw new RuntimeException("Error getting the field's value", e);
    }
  }

  public static float getFloatValue(final Field field, final Object object) {
    try {
      return field.getFloat(object);
    } catch (final IllegalAccessException e) {
      throw new RuntimeException("Error getting the field's value", e);
    }
  }
}
