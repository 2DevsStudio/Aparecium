/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.factory;

import static com.ignitedev.aparecium.Constants.DEFAULT_FACTORY;

import java.util.HashMap;
import java.util.Map;

/**
 * @implNote Factory Cache, you can register new factories here, remember to do it every time you
 *     start plugin, this cache is not persistent
 */
public class FactoryHolder<T extends Factory> {

  private final Map<String, T> factories = new HashMap<>();

  public void registerFactory(String id, T factory) {
    factories.putIfAbsent(id, factory);
  }

  public T getFactory(String id) {
    return factories.get(id);
  }

  public T getDefaultFactory() {
    return factories.get(DEFAULT_FACTORY);
  }
}
