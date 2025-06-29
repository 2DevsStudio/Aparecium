/*
 * Copyright (c) 2022. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.util;

import java.util.concurrent.ThreadLocalRandom;
import lombok.experimental.UtilityClass;

/**
 * Utility class for generating random numbers and performing random calculations.
 * Provides methods for generating random integers, calculating chances, and more.
 */
@UtilityClass
public final class RandomUtility {

  /**
   * Calculates the number of successful chances based on a given probability.
   *
   * @param chance The probability of success (as a percentage).
   * @param bound The number of attempts to perform.
   * @return The number of successful attempts.
   */
  public int nextChance(double chance, int bound) {
    int result = 0;

    for (int i = 0; i < bound; i++) {
      result += ThreadLocalRandom.current().nextDouble() < (chance / 100) ? 1 : 0;
    }
    return result;
  }

  /**
   * Generates a random integer within the range [0, bound).
   *
   * @param bound The upper bound (exclusive) for the random number.
   * @return A random integer within the specified range.
   */
  public int nextInt(int bound) {
    return ThreadLocalRandom.current().nextInt(bound);
  }

  /**
   * Generates a random integer within a specified range, scaled by an amount and adjusted by an exponent.
   *
   * @param min The minimum value of the range.
   * @param max The maximum value of the range.
   * @param amount The scaling factor for the range.
   * @param exp The exponent used to adjust the distribution.
   * @return A random integer within the adjusted range.
   */
  public int nextInt(int min, int max, int amount, double exp) {

    min *= amount;
    max *= amount;

    double power = Math.pow(nextFloat(), exp);
    double bound = ((double) (max - min + 1)) * power;
    int rounded = Math.max(1, (int) Math.round(bound));

    return nextInt(rounded) + min;
  }

  /**
   * Generates a random integer within a specified range, scaled by an amount.
   * Uses Gaussian distribution for larger amounts.
   *
   * @param min The minimum value of the range.
   * @param max The maximum value of the range.
   * @param amount The scaling factor for the range.
   * @return A random integer within the adjusted range.
   */
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

  /**
   * Generates a random value using Gaussian distribution.
   *
   * @return A random value based on Gaussian distribution.
   */
  private double nextGaussian() {
    return ThreadLocalRandom.current().nextGaussian();
  }

  /**
   * Generates a random float value within the range [0.0, 1.0).
   *
   * @return A random float value.
   */
  private float nextFloat() {
    return ThreadLocalRandom.current().nextFloat();
  }

  /**
   * Calculates the average of two integers.
   *
   * @param min The minimum value.
   * @param max The maximum value.
   * @return The average of the two values.
   */
  private int getAverage(int min, int max) {
    return (max + min) / 2;
  }

  /**
   * Calculates the standard deviation based on the maximum and average values.
   *
   * @param max The maximum value.
   * @param avg The average value.
   * @return The standard deviation.
   */
  private int getSD(int max, int avg) {
    return (max - avg) / 3;
  }

  /**
   * Ensures a number is within a specified range.
   *
   * @param min The minimum value of the range.
   * @param max The maximum value of the range.
   * @param num The number to check.
   * @return The number adjusted to be within the range.
   */
  private int ensureRange(int min, int max, int num) {
    return Math.min(max, Math.max(min, num));
  }
}