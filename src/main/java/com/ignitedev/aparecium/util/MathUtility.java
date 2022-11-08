/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.util;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import lombok.experimental.UtilityClass;

@SuppressWarnings("unused")
@UtilityClass
public class MathUtility {

  private final TreeMap<Integer, String> romanNumerals = new TreeMap<>();
  private final int BF_SIN_BITS, BF_SIN_MASK, BF_SIN_COUNT;
  private final float BF_radFull, BF_radToIndex;
  private final float BF_degFull, BF_degToIndex;
  private final float[] BF_sin, BF_cos;

  static {
    romanNumerals.put(1000, "M");
    romanNumerals.put(900, "CM");
    romanNumerals.put(500, "D");
    romanNumerals.put(400, "CD");
    romanNumerals.put(100, "C");
    romanNumerals.put(90, "XC");
    romanNumerals.put(50, "L");
    romanNumerals.put(40, "XL");
    romanNumerals.put(10, "X");
    romanNumerals.put(9, "IX");
    romanNumerals.put(5, "V");
    romanNumerals.put(4, "IV");
    romanNumerals.put(1, "I");

    BF_SIN_BITS = 12;
    BF_SIN_MASK = ~(-1 << BF_SIN_BITS);
    BF_SIN_COUNT = BF_SIN_MASK + 1;

    BF_radFull = (float) (Math.PI * 2.0);
    BF_degFull = (float) (360.0);
    BF_radToIndex = BF_SIN_COUNT / BF_radFull;
    BF_degToIndex = BF_SIN_COUNT / BF_degFull;

    BF_sin = new float[BF_SIN_COUNT];
    BF_cos = new float[BF_SIN_COUNT];

    for (int i = 0; i < BF_SIN_COUNT; i++) {
      BF_sin[i] = (float) Math.sin((i + 0.5f) / BF_SIN_COUNT * BF_radFull);
      BF_cos[i] = (float) Math.cos((i + 0.5f) / BF_SIN_COUNT * BF_radFull);
    }

    // Four cardinal directions (credits: Nate)
    for (int i = 0; i < 360; i += 90) {
      BF_sin[(int) (i * BF_degToIndex) & BF_SIN_MASK] = (float) Math.sin(i * Math.PI / 180.0);
      BF_cos[(int) (i * BF_degToIndex) & BF_SIN_MASK] = (float) Math.cos(i * Math.PI / 180.0);
    }
  }

  /**
   * Convert a number to roman numerals
   *
   * @param number The number to convert over
   */
  public String toRoman(int number) {
    int l = romanNumerals.floorKey(number);
    if (number == l) {
      return romanNumerals.get(number);
    }
    return romanNumerals.get(l) + toRoman(number - l);
  }

  public double normalizeDouble(double toNormalize) {
    return (double) (int) (toNormalize * 100) / 100;
  }

  /**
   * This will check if the number is between to given numbers
   *
   * @param input This is the number you want to check that is between the 2 values
   * @param min This is the min value of the check
   * @param max This is the max value of the check
   * @return boolean This returns if the number is between the 2 given values
   */
  public boolean isBetween(Integer input, Integer min, Integer max) {
    return input >= min && input <= max;
  }

  /**
   * Converting any given number to be positive
   *
   * @param input This is the number you want to convert to become positive
   * @return int This returns the number given positive
   */
  public int convertToPositive(int input) {
    return Math.abs(input);
  }

  /**
   * This will return the right plural
   *
   * @param text This is the text you want to correct
   * @param input This is the amount we use to check if more than one
   * @return String This returns the correct plural statement
   */
  public String getCorrectPlural(String text, int input) {
    return text + (input == 1 ? "" : "s");
  }

  /**
   * A chance system that goes up to 100
   *
   * @param percent This is the number we used to chance
   * @return boolean This returns the chance.
   */
  public boolean getRandomPercent(int percent) {
    return ThreadLocalRandom.current().nextInt(100) <= percent;
  }

  /**
   * A chance system that goes up to 100
   *
   * @param percent This is the number we use to chance
   * @return boolean This returns the chance.
   */
  public boolean getRandomPercent(double percent) {
    return ThreadLocalRandom.current().nextDouble() <= percent;
  }

  /**
   * Choose a random number between 2 values
   *
   * @param min This will be the min value we choose from
   * @param max This will be the max value we choose from
   * @return int This returns the random generated number between the 2 values
   */
  public int getRandomNumber(int min, int max) {
    int realMin = Math.min(min, max);
    int realMax = Math.max(min, max);
    int exclusiveSize = realMax - realMin;

    return ThreadLocalRandom.current().nextInt(exclusiveSize + 1) + realMin;
  }

  /**
   * Choose a random number between 2 values
   *
   * @param min This will be the min value we choose from
   * @param max This will be the max value we choose from
   * @return double This returns the random generated number between the 2 values
   */
  public double getRandomNumber(double min, double max) {
    double realMin = Math.min(min, max);
    double realMax = Math.max(min, max);
    double exclusiveSize = realMax - realMin;

    return ThreadLocalRandom.current().nextDouble() * exclusiveSize + realMin;
  }

  /**
   * Get the color that represents the percentage
   *
   * @param percent This is the percentage value
   * @return Color This returns the color that represents that color bar
   */
  public Color getColor(int percent) {
    float f1 = 100;
    float f2 = Math.max(0.0F, Math.min((float) percent, f1) / f1);
    int color = Color.HSBtoRGB(f2 / 3.0F, 1.0F, 1.0F) | 0xFF000000;
    return new Color(color);
  }

  /**
   * Checks if a specific {@link Integer} is in the given range. If not the respective bound of the
   * range is returned.
   *
   * @param value the value which should be checked.
   * @param max the maximum value.
   * @param min the minimum value
   * @return the calculated value.
   */
  public int getMaxOrMin(int value, int max, int min) {
    return value < max ? (Math.max(value, min)) : max;
  }

  public float sin(double rad) {
    return BF_sin[(int) (rad * BF_radToIndex) & BF_SIN_MASK];
  }

  public float cos(double rad) {
    return BF_cos[(int) (rad * BF_radToIndex) & BF_SIN_MASK];
  }

  public double lengthSq(double x, double y, double z) {
    return (x * x) + (y * y) + (z * z);
  }

  public double lengthSq(double x, double z) {
    return (x * x) + (z * z);
  }

  public boolean chanceOfPercent(Number percent) {
    if (percent.doubleValue() <= 0.0) {
      return false;
    }
    return chanceOfDecimal(percent.doubleValue() / 100.0);
  }

  public boolean chanceOfDecimal(Number decimal) {
    if (decimal.doubleValue() <= 0.0) {
      return false;
    }
    return Math.random() < decimal.doubleValue();
  }

  public Double round(double value, int places) {
    if (places < 0) {
      throw new IllegalArgumentException();
    }
    return BigDecimal.valueOf(value).setScale(places, RoundingMode.HALF_DOWN).doubleValue();
  }
}
