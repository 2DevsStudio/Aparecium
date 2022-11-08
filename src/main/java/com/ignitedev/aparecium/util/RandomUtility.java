/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.util;

import java.util.concurrent.ThreadLocalRandom;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class RandomUtility {

  public int nextChance(double chance, int bound) {
    int result = 0;

    for (int i = 0; i < bound; i++) {
      result += ThreadLocalRandom.current().nextDouble() < (chance / 100) ? 1 : 0;
    }
    return result;
  }

  public int nextInt(int bound) {
    return ThreadLocalRandom.current().nextInt(bound);
  }

  public int nextInt(int min, int max, int amount, double exp) {

    min *= amount;
    max *= amount;

    double power = Math.pow(nextFloat(), exp);
    double bound = ((double) (max - min + 1)) * power;
    int rounded = Math.max(1, (int) Math.round(bound));

    return nextInt(rounded) + min;
  }

  public int nextInt(int min, int max, int amount) {

    min *= amount;
    max *= amount;

    if (amount < 10) {
      return nextInt(max - min + 1) + min;
    } else {
      int avg = getAverage(min, max);
      return ensureRange(min, max, (int) Math.round(nextGaussian() * getSD(max, avg) + avg));
    }
  }

  private double nextGaussian() {
    return ThreadLocalRandom.current().nextGaussian();
  }

  private float nextFloat() {
    return ThreadLocalRandom.current().nextFloat();
  }

  private int getAverage(int min, int max) {
    return (max + min) / 2;
  }

  private int getSD(int max, int avg) {
    return (max - avg) / 3;
  }

  private int ensureRange(int min, int max, int num) {
    return Math.min(max, Math.max(min, num));
  }
}
