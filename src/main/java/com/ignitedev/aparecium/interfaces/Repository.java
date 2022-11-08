/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.interfaces;

import org.jetbrains.annotations.Nullable;

public interface Repository<T> {

  @Nullable
  T findById(String identifier);

  void remove(String identifier);

  void add(T value);
}
