package com.ignitedev.aparecium.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.internal.Primitives;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;

@SuppressWarnings("unused")
@UtilityClass
public class ReflectionUtility {

  private final Map<Class<?>, Constructor<?>> CONSTRUCTOR_MAP = new HashMap<>();
  private final Map<Class<?>, Map<String, Method>> METHOD_MAP = new HashMap<>();
  private final Map<Class<?>, Map<String, Field>> FIELD_MAP = new HashMap<>();

  public Constructor<?> getConstructor(Class<?> clazz, Class<?>... parameterTypes)
      throws Exception {

    if (CONSTRUCTOR_MAP.containsKey(clazz)) {
      return CONSTRUCTOR_MAP.get(clazz);
    }

    for (Constructor<?> constructor :
        merge(clazz.getDeclaredConstructors(), clazz.getConstructors())) {

      if (compareClasses(parameterTypes, constructor.getParameterTypes())) {
        continue;
      }

      constructor.setAccessible(true);
      CONSTRUCTOR_MAP.put(clazz, constructor);
      return constructor;
    }

    throw new NoSuchMethodException(
        "There is no such constructor in this class with the specified parameter types");
  }

  public Object initializeObject(Class<?> clazz, Object... arguments) throws Exception {

    return getConstructor(
            clazz,
            Arrays.stream(arguments)
                .filter(Objects::nonNull)
                .map(Object::getClass)
                .toArray(Class[]::new))
        .newInstance(arguments);
  }

  public Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {

    if (METHOD_MAP.containsKey(clazz) && METHOD_MAP.get(clazz).containsKey(methodName)) {
      return METHOD_MAP.get(clazz).get(methodName);
    }

    for (Method method : merge(clazz.getMethods(), clazz.getDeclaredMethods())) {
      if (!method.getName().equalsIgnoreCase(methodName)
          || compareClasses(parameterTypes, method.getParameterTypes())) {
        continue;
      }

      method.setAccessible(true);

      if (METHOD_MAP.containsKey(clazz)) {
        METHOD_MAP.get(clazz).put(methodName, method);
      } else {
        Map<String, Method> methodMap = new HashMap<>();
        methodMap.put(methodName, method);
        METHOD_MAP.put(clazz, methodMap);
      }
      return method;
    }

    return null;
  }

  private boolean compareClasses(Class<?>[] first, Class<?>[] second) {

    if (first.length != second.length) {
      return true;
    }
    for (int i = 0; i < first.length; i++) {
      Class<?> c1 = Primitives.unwrap(first[i]);
      Class<?> c2 = Primitives.unwrap(second[i]);
      if (c1 != c2 && PrimitivesCaster.isNotCastable(c1, c2)) {
        return true;
      }
    }
    return false;
  }

  public Object invokeMethod(Object instance, String methodName, Object... arguments)
      throws Exception {

    return getMethod(
            instance.getClass(),
            methodName,
            Arrays.stream(arguments)
                .filter(Objects::nonNull)
                .map(Object::getClass)
                .toArray(Class[]::new))
        .invoke(instance, arguments);
  }

  public Object invokeMethod(
      Object instance, Class<?> clazz, String methodName, Object... arguments) throws Exception {

    return getMethod(
            clazz,
            methodName,
            Arrays.stream(arguments)
                .filter(Objects::nonNull)
                .map(Object::getClass)
                .toArray(Class[]::new))
        .invoke(instance, arguments);
  }

  public Field getField(Class<?> clazz, String fieldName) {

    Map<String, Field> stringFieldMap = FIELD_MAP.computeIfAbsent(clazz, clazz2 -> new HashMap<>());
    Field field = stringFieldMap.get(fieldName);
    if (field != null) {
      return field;
    }

    try {
      field = clazz.getDeclaredField(fieldName);
      field.setAccessible(true);
      stringFieldMap.put(fieldName, field);
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    }
    return field;
  }

  public <T> Field getField(Class<?> target, String name, Class<T> fieldType, int index) {

    for (final Field field : target.getDeclaredFields()) {
      if ((name == null || field.getName().equals(name))
          && fieldType.isAssignableFrom(field.getType())
          && index-- <= 0) {
        field.setAccessible(true);
        return field;
      }
    }

    // Search in parent classes
    if (target.getSuperclass() != null) {
      return getField(target.getSuperclass(), name, fieldType, index);
    }

    throw new IllegalArgumentException("Cannot find field with type " + fieldType);
  }

  public Field getField(Class<?> clazz, Class<?> type) {

    try {
      return FieldUtils.getAllFieldsList(clazz).stream()
          .filter(
              field -> {
                field.setAccessible(true);
                return field.getType().isAssignableFrom(type) || type == field.getType();
              })
          .findFirst()
          .orElse(null);
    } catch (Throwable thrw) {
      throw new IllegalStateException("Failed to find field with type " + type.getSimpleName());
    }
  }

  @SafeVarargs
  private <T> Set<T> merge(T[]... arrays) {

    Set<T> set = Sets.newHashSet();
    for (T[] array : arrays) {
      set.addAll(Lists.newArrayList(array));
    }

    return set;
  }

  @SneakyThrows
  public Object executeMethod(Method method, Object ofWho, Object... args) {
    args = checkArgs(method.getParameterTypes(), args);
    return method.invoke(ofWho, args);
  }

  @SneakyThrows
  public Object initializeObject(Constructor<?> constructor, Object... args) {

    args = checkArgs(constructor.getParameterTypes(), args);
    return constructor.newInstance(args);
  }

  private Object[] checkArgs(Class<?>[] a1, Object[] a2) throws IllegalStateException {

    Preconditions.checkArgument(
        a1.length == a2.length, "Failed to validate arguments cause lengths doesn't match");
    int size = a1.length;
    for (int i = 0; i < size; i++) {
      Class<?> c1 = Primitives.unwrap(a1[i]);
      Object o1 = a2[i];
      Class<?> c2 = Primitives.unwrap(o1.getClass());

      if (c1 != c2 && PrimitivesCaster.isNotCastable(c1, c2)) {
        throw new IllegalStateException(
            "Argument miss match at "
                + i
                + " found "
                + c2.getSimpleName()
                + ", required: "
                + c1.getSimpleName());
      } else {
        if (a1[i] == o1.getClass()) {
          continue;
        }
        a2[i] = PrimitivesCaster.cast(c1, o1);
      }
    }
    return a2;
  }

  @SneakyThrows
  public void setValue(Object object, String field, Object value) {

    getField(object.getClass(), field).set(object, value);
  }

  public Method getTypedMethod(
      Class<?> clazz, String methodName, Class<?> returnType, Class<?>... params) {

    for (final Method method : clazz.getDeclaredMethods()) {
      if ((methodName == null || method.getName().equals(methodName))
          && (returnType == null || method.getReturnType().equals(returnType))
          && Arrays.equals(method.getParameterTypes(), params)) {
        method.setAccessible(true);
        return method;
      }
    }

    // Search in every superclass
    if (clazz.getSuperclass() != null) {
      return getMethod(clazz.getSuperclass(), methodName, params);
    }

    throw new IllegalStateException(
        String.format("Unable to find method %s (%s).", methodName, Arrays.asList(params)));
  }

  public static class PrimitivesCaster {

    private static final Map<Class<?>, Function<String, Object>> casters = new HashMap<>();

    static {
      casters.put(
          int.class,
          in -> {
            if (in.contains(".")) {
              return Math.round(Double.parseDouble(in));
            } else {
              return Integer.parseInt(in);
            }
          });
      casters.put(
          long.class,
          in -> {
            if (in.contains(".")) {
              return Math.round(Double.parseDouble(in));
            } else {
              return Long.parseLong(in);
            }
          });
      casters.put(double.class, Double::parseDouble);
      casters.put(
          short.class,
          in -> {
            if (in.contains(".")) {
              return Math.round(Double.parseDouble(in));
            } else {
              return Short.parseShort(in);
            }
          });
      casters.put(float.class, Float::parseFloat);
      casters.put(byte.class, Byte::parseByte);
      casters.put(boolean.class, Boolean::valueOf);
    }

    public static boolean isNotCastable(Class<?> from, Class<?> to) {

      return !casters.containsKey(from) || !casters.containsKey(to);
    }

    public static Object cast(Class<?> to, @NonNull Object what) {

      Function<String, Object> stringObjectFunction = casters.get(Primitives.unwrap(to));
      Preconditions.checkArgument(
          stringObjectFunction != null, "Failed to find caster for " + to.getSimpleName());

      return stringObjectFunction.apply(what.toString());
    }
  }

  public static Object getPrivateField(Object object, String field)
      throws SecurityException, NoSuchFieldException, IllegalArgumentException,
          IllegalAccessException {
    Class<?> clazz = object.getClass();
    Field objectField = clazz.getDeclaredField(field);
    objectField.setAccessible(true);
    Object result = objectField.get(object);
    objectField.setAccessible(false);
    return result;
  }
}
