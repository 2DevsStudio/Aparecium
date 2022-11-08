/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.util.collections;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

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

  public void add(double weight, E result) {
    if (weight <= 0) {
      return;
    }
    total += weight;
    map.put(total, result);
  }

  public E next() {
    double value = random.nextDouble() * total;
    return map.higherEntry(value).getValue();
  }
}
