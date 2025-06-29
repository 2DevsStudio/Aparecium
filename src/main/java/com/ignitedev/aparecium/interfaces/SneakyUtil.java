/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.interfaces;

public abstract class SneakyUtil {

  @SuppressWarnings("unchecked")
  static <E extends Exception> void sneakyThrow(Exception exception) throws E {
    throw (E) exception;
  }
}
