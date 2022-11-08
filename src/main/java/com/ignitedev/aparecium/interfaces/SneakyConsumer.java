/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.interfaces;

import java.util.function.Consumer;

public interface SneakyConsumer<T, E extends Exception> {

  static <T> Consumer<T> of(SneakyConsumer<? super T, ? extends Exception> consumer) {
    return value -> {
      try {
        consumer.accept(value);
      } catch (Exception exception) {
        SneakyUtil.sneakyThrow(exception);
      }
    };
  }

  void accept(T t) throws E;
}
