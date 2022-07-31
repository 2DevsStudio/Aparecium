package com.ignitedev.aparecium.interfaces;

public abstract class SneakyUtil {

  @SuppressWarnings("unchecked")
  static <E extends Exception> void sneakyThrow(Exception exception) throws E {
    throw (E) exception;
  }
}
