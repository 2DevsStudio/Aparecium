/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.internal.Primitives;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.reflect.FieldUtils;

/**
 * Utility class for performing reflection-based operations.
 * Provides methods for accessing constructors, methods, fields, and invoking them dynamically.
 */
@SuppressWarnings("unused")
@UtilityClass
public class ReflectionUtility {

  /** Cache for storing constructors of classes. */
  private final Map<Class<?>, Constructor<?>> CONSTRUCTOR_MAP = new HashMap<>();

  /** Cache for storing methods of classes. */
  private final Map<Class<?>, Map<String, Method>> METHOD_MAP = new HashMap<>();

  /** Cache for storing fields of classes. */
  private final Map<Class<?>, Map<String, Field>> FIELD_MAP = new HashMap<>();

  /**
   * Retrieves a constructor of a class with the specified parameter types.
   *
   * @param clazz The class to retrieve the constructor from.
   * @param parameterTypes The parameter types of the constructor.
   * @return The matching constructor.
   * @throws Exception If no matching constructor is found.
   */
  public Constructor<?> getConstructor(Class<?> clazz, Class<?>... parameterTypes) throws Exception {
    if (CONSTRUCTOR_MAP.containsKey(clazz)) {
      return CONSTRUCTOR_MAP.get(clazz);
    }

    for (Constructor<?> constructor : merge(clazz.getDeclaredConstructors(), clazz.getConstructors())) {
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

  /**
   * Initializes an object using the specified class and arguments.
   *
   * @param clazz The class to initialize the object from.
   * @param arguments The arguments to pass to the constructor.
   * @return The initialized object.
   * @throws Exception If initialization fails.
   */
  public Object initializeObject(Class<?> clazz, Object... arguments) throws Exception {
    return getConstructor(
            clazz,
            Arrays.stream(arguments)
                    .filter(Objects::nonNull)
                    .map(Object::getClass)
                    .toArray(Class[]::new))
            .newInstance(arguments);
  }

  /**
   * Retrieves a method of a class with the specified name and parameter types.
   *
   * @param clazz The class to retrieve the method from.
   * @param methodName The name of the method.
   * @param parameterTypes The parameter types of the method.
   * @return The matching method, or null if not found.
   */
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

  /**
   * Compares two arrays of classes to check if they match.
   *
   * @param first The first array of classes.
   * @param second The second array of classes.
   * @return True if the arrays do not match, false otherwise.
   */
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

  /**
   * Invokes a method on an instance with the specified arguments.
   *
   * @param instance The instance to invoke the method on.
   * @param methodName The name of the method.
   * @param arguments The arguments to pass to the method.
   * @return The result of the method invocation.
   * @throws Exception If invocation fails.
   */
  public Object invokeMethod(Object instance, String methodName, Object... arguments) throws Exception {
    return getMethod(
            instance.getClass(),
            methodName,
            Arrays.stream(arguments)
                    .filter(Objects::nonNull)
                    .map(Object::getClass)
                    .toArray(Class[]::new))
            .invoke(instance, arguments);
  }

  /**
   * Invokes a method on an instance of a specific class with the specified arguments.
   *
   * @param instance The instance to invoke the method on.
   * @param clazz The class of the method.
   * @param methodName The name of the method.
   * @param arguments The arguments to pass to the method.
   * @return The result of the method invocation.
   * @throws Exception If invocation fails.
   */
  public Object invokeMethod(Object instance, Class<?> clazz, String methodName, Object... arguments) throws Exception {
    return getMethod(
            clazz,
            methodName,
            Arrays.stream(arguments)
                    .filter(Objects::nonNull)
                    .map(Object::getClass)
                    .toArray(Class[]::new))
            .invoke(instance, arguments);
  }

  /**
   * Retrieves a field of a class with the specified name.
   *
   * @param clazz The class to retrieve the field from.
   * @param fieldName The name of the field.
   * @return The matching field, or null if not found.
   */
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

  /**
   * Retrieves a field of a class with the specified name, type, and index.
   *
   * @param target The class to retrieve the field from.
   * @param name The name of the field (optional).
   * @param fieldType The type of the field.
   * @param index The index of the field.
   * @return The matching field.
   * @throws IllegalArgumentException If no matching field is found.
   */
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

  /**
   * Retrieves a field of a class with the specified type.
   *
   * @param clazz The class to retrieve the field from.
   * @param type The type of the field.
   * @return The matching field, or null if not found.
   * @throws IllegalStateException If retrieval fails.
   */
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

  /**
   * Merges multiple arrays into a single set.
   *
   * @param arrays The arrays to merge.
   * @param <T> The type of elements in the arrays.
   * @return A set containing all elements from the arrays.
   */
  @SafeVarargs
  private <T> Set<T> merge(T[]... arrays) {
    Set<T> set = Sets.newHashSet();
    for (T[] array : arrays) {
      set.addAll(Lists.newArrayList(array));
    }
    return set;
  }

  /**
   * Executes a method with the specified arguments.
   *
   * @param method The method to execute.
   * @param ofWho The instance to execute the method on.
   * @param args The arguments to pass to the method.
   * @return The result of the method execution.
   */
  @SneakyThrows
  public Object executeMethod(Method method, Object ofWho, Object... args) {
    args = checkArgs(method.getParameterTypes(), args);
    return method.invoke(ofWho, args);
  }

  /**
   * Initializes an object using the specified constructor and arguments.
   *
   * @param constructor The constructor to use.
   * @param args The arguments to pass to the constructor.
   * @return The initialized object.
   */
  @SneakyThrows
  public Object initializeObject(Constructor<?> constructor, Object... args) {
    args = checkArgs(constructor.getParameterTypes(), args);
    return constructor.newInstance(args);
  }

  /**
   * Validates and adjusts arguments to match the expected parameter types.
   *
   * @param a1 The expected parameter types.
   * @param a2 The provided arguments.
   * @return The adjusted arguments.
   * @throws IllegalStateException If validation fails.
   */
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

  /**
   * Sets the value of a field on an object.
   *
   * @param object The object to set the field value on.
   * @param field The name of the field.
   * @param value The value to set.
   */
  @SneakyThrows
  public void setValue(Object object, String field, Object value) {
    getField(object.getClass(), field).set(object, value);
  }

  /**
   * Retrieves a method of a class with the specified name, return type, and parameters.
   *
   * @param clazz The class to retrieve the method from.
   * @param methodName The name of the method.
   * @param returnType The return type of the method.
   * @param params The parameter types of the method.
   * @return The matching method.
   * @throws IllegalStateException If no matching method is found.
   */
  public Method getTypedMethod(Class<?> clazz, String methodName, Class<?> returnType, Class<?>... params) {
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

  /**
   * Retrieves the value of a private field on an object.
   *
   * @param object The object to retrieve the field value from.
   * @param field The name of the field.
   * @return The value of the field.
   * @throws SecurityException If access to the field is denied.
   * @throws NoSuchFieldException If the field does not exist.
   * @throws IllegalArgumentException If the field cannot be accessed.
   * @throws IllegalAccessException If the field cannot be accessed.
   */
  public static Object getPrivateField(Object object, String field)
          throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
    Class<?> clazz = object.getClass();
    Field objectField = clazz.getDeclaredField(field);
    objectField.setAccessible(true);
    Object result = objectField.get(object);
    objectField.setAccessible(false);
    return result;
  }

  /**
   * Utility class for casting primitive types.
   * Provides methods for checking castability and performing casts.
   */
  public static class PrimitivesCaster {

    /** Map of primitive types to their corresponding casting functions. */
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

    /**
     * Checks if two classes are not castable to each other.
     *
     * @param from The source class.
     * @param to The target class.
     * @return True if the classes are not castable, false otherwise.
     */
    public static boolean isNotCastable(Class<?> from, Class<?> to) {
      return !casters.containsKey(from) || !casters.containsKey(to);
    }

    /**
     * Casts an object to the specified primitive type.
     *
     * @param to The target primitive type.
     * @param what The object to cast.
     * @return The casted object.
     * @throws IllegalArgumentException If casting fails.
     */
    public static Object cast(Class<?> to, @NonNull Object what) {
      Function<String, Object> stringObjectFunction = casters.get(Primitives.unwrap(to));
      Preconditions.checkArgument(
              stringObjectFunction != null, "Failed to find caster for " + to.getSimpleName());

      return stringObjectFunction.apply(what.toString());
    }
  }
}