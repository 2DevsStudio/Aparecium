package com.ignitedev.aparecium.interfaces;

import java.util.function.Function;

public interface SneakyFunction<T, U, E extends Exception> {

  static <T, U> Function<T, U> of(SneakyFunction<? super T, U, ? extends Exception> consumer) {
    return value -> {
      try {
        return consumer.apply(value);
      } catch (Exception exception) {
        SneakyUtil.sneakyThrow(exception);
      }
      return null;
    };
  }

  U apply(T t) throws E;
}
