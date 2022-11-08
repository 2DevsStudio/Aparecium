/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.util.collection;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * @implNote That's query and mapping utility for WorldGuard, you can as well create a world-guard
 *     region using it.
 */
@SuppressWarnings("unused")
public class WeightCollection<E> {

  private final NavigableMap<Double, E> map = new TreeMap<>();

  private final Random random;
  private double total = 0;

  public WeightCollection() {
    this(new Random());
  }

  public WeightCollection(Random random) {
    this.random = random;
  }

  /**
   * @param weight of your specified result in argument, higher weight means higher chance, you
   *     cannot specify weight bellow 0
   * @param result element that you would like to collection
   */
  public void add(double weight, E result) {
    if (weight <= 0) {
      return;
    }
    this.total += weight;
    this.map.put(total, result);
  }

  /**
   * @return generate random entry depending on weight of each item
   */
  public E next() {
    double value = random.nextDouble() * total;
    return this.map.higherEntry(value).getValue();
  }

  /**
   * @return clone of map
   */
  public NavigableMap<Double, E> getMap() {
    return new TreeMap<>(map);
  }

  public void set(TreeMap<Double, E> treeMap) {
    this.map.putAll(treeMap);
  }
}
