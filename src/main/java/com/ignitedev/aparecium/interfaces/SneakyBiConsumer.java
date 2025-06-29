/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.interfaces;

import java.util.function.BiConsumer;

public interface SneakyBiConsumer<T, U, E extends Exception> {

  static <T, U> BiConsumer<T, U> of(
      SneakyBiConsumer<? super T, ? super U, ? extends Exception> consumer) {
    return (firstValue, secondValue) -> {
      try {
        consumer.accept(firstValue, secondValue);
      } catch (Exception ex) {
        SneakyUtil.sneakyThrow(ex);
      }
    };
  }

  void accept(T t, U u) throws E;
}
