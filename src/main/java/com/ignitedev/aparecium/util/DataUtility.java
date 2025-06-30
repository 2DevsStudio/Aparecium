/*
 * Copyright (c) 2025. Made by 2DevsStudio LLC ( https://2devsstudio.com/ ), using one of our available slaves: IgniteDEV. All rights reserved.
 */

package com.ignitedev.aparecium.util;

import com.ignitedev.aparecium.Aparecium;
import com.ignitedev.aparecium.component.ApareciumComponent;
import com.ignitedev.aparecium.util.text.Placeholder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.material.Crops;

/**
 * Utility class providing various helper methods for data manipulation, validation, and common
 * operations.
 */
@SuppressWarnings("unused")
@UtilityClass
public class DataUtility {

  /**
   * Updates or adds a placeholder in the provided list.
   *
   * @param placeholdersToUpdate The list of placeholders to update.
   * @param dataToUpdate The key of the placeholder to update.
   * @param updateValue The new value for the placeholder.
   * @return The updated list of placeholders.
   */
  public List<Placeholder> updateData(
      List<Placeholder> placeholdersToUpdate, String dataToUpdate, String updateValue) {

    AtomicBoolean isDataFound = new AtomicBoolean(false);
    ArrayList<Placeholder> updatedList = new ArrayList<>(placeholdersToUpdate);

    updatedList.forEach(
        placeholder -> {
          String asString = placeholder.getKey().getAsString();

          if (asString != null && asString.equalsIgnoreCase(dataToUpdate)) {
            placeholder.setValue(ApareciumComponent.of(updateValue));
            isDataFound.set(true);
          }
        });
    if (!isDataFound.get()) {
      updatedList.add(
          new Placeholder(ApareciumComponent.of(dataToUpdate), ApareciumComponent.of(updateValue)));
    }

    return updatedList;
  }

  /**
   * Checks if a string is a valid UUID.
   *
   * @param string The string to check.
   * @return True if the string is a valid UUID, false otherwise.
   */
  @SuppressWarnings("ResultOfMethodCallIgnored")
  public boolean isUUID(String string) {
    try {
      UUID.fromString(string);
      return true;
    } catch (Exception exception) {
      return false;
    }
  }

  /**
   * Checks if an enum contains a specific value.
   *
   * @param enumValues The array of enum values.
   * @param string The value to check.
   * @param <T> The type of the enum.
   * @return True if the enum contains the value, false otherwise.
   */
  public <T extends Enum<?>> boolean enumsContains(T[] enumValues, String string) {
    for (T enumValue : enumValues) {
      if (enumValue.name().equalsIgnoreCase(string)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if a string represents a boolean value ("true" or "false").
   *
   * @param string The string to check.
   * @return True if the string is a boolean value, false otherwise.
   */
  public boolean isBoolean(String string) {
    return string.equalsIgnoreCase("true") || string.equalsIgnoreCase("false");
  }

  /**
   * Removes a specified number of elements from the start of a list.
   *
   * @param list The list to modify.
   * @param elements The number of elements to remove.
   * @param <T> The type of elements in the list.
   * @throws IllegalArgumentException If the number of elements to remove exceeds the list size.
   */
  public <T> void removeListElements(List<T> list, int elements) {
    if (list.size() <= elements) {
      throw new IllegalArgumentException("Removing more elements than list contains!");
    }
    if (elements > 0) {
      list.subList(0, elements).clear();
    }
  }

  /**
   * Returns a random element from an array.
   *
   * @param list The array to select from.
   * @param <T> The type of elements in the array.
   * @return A random element from the array.
   */
  public <T> T getRandomElement(T[] list) {
    return list[ThreadLocalRandom.current().nextInt(list.length)];
  }

  /**
   * Flattens a 2D string array into a 1D array.
   *
   * @param data The 2D array to flatten.
   * @return A 1D array containing all elements of the 2D array.
   */
  public String[] flatten(String[][] data) {
    List<String> list = new ArrayList<>();
    for (String[] datum : data) {
      list.addAll(Arrays.asList(datum));
    }
    return list.toArray(new String[0]);
  }

  /**
   * Flattens a 2D integer array into a 1D array.
   *
   * @param data The 2D array to flatten.
   * @return A 1D array containing all elements of the 2D array.
   */
  public int[] flatten(int[][] data) {
    int[] arr = new int[totalSize(data)];
    int j = 0;
    for (int[] datum : data) {
      for (int i : datum) {
        arr[j] = i;
        j++;
      }
    }
    return arr;
  }

  /**
   * Calculates the total size of a 2D integer array.
   *
   * @param a2 The 2D array.
   * @return The total number of elements in the array.
   */
  public int totalSize(int[][] a2) {
    int result = 0;
    for (int[] a1 : a2) {
      result += a1.length;
    }
    return result;
  }

  /**
   * Replaces text on a sign.
   *
   * @param signState The sign to modify.
   * @param from The text to replace.
   * @param to The replacement text.
   */
  public void replaceSignText(Sign signState, String from, String to) {
    if (signState == null) {
      return;
    }
    int x = 0;
    for (Component line : signState.lines()) {
      signState.line(x, line.replaceText((value) -> value.matchLiteral(from).replacement(to)));
      x++;
    }
  }

  /**
   * Merges multiple lists into one.
   *
   * @param lists The lists to merge.
   * @param <C> The type of elements in the lists.
   * @return A single list containing all elements from the input lists.
   */
  @SafeVarargs
  public <C> List<C> mergeLists(List<C>... lists) {
    List<C> returnList = new ArrayList<>();

    for (List<C> list : lists) {
      returnList.addAll(list);
    }
    return returnList;
  }

  /**
   * Rounds a double value to a specified number of decimal places.
   *
   * @param value The value to round.
   * @param precision The number of decimal places.
   * @return The rounded value.
   */
  public double round(double value, int precision) {
    int scale = (int) Math.pow(10, precision);
    return (double) Math.round(value * scale) / scale;
  }

  /**
   * Checks if a block is a crop.
   *
   * @param block The block to check.
   * @return True if the block is a crop, false otherwise.
   */
  public boolean isCrop(Block block) {
    return block.getState().getData() instanceof Crops;
  }

  /**
   * Checks if it is day in a given world.
   *
   * @param world The world to check.
   * @return True if it is day, false otherwise.
   */
  private boolean isDay(World world) {
    long time = world.getTime();
    return time < 12300 || time > 23850;
  }

  /**
   * Converts a millisecond duration to a human-readable string format.
   *
   * @param millis The duration in milliseconds.
   * @return A string representing the duration in the format "X Hours Y Minutes Z Seconds".
   * @throws IllegalArgumentException If the duration is negative.
   */
  public String getDurationBreakdown(long millis) {
    if (millis < 0) {
      throw new IllegalArgumentException("Duration must be greater than zero!");
    }
    long days = TimeUnit.MILLISECONDS.toDays(millis);
    millis -= TimeUnit.DAYS.toMillis(days);
    long hours = TimeUnit.MILLISECONDS.toHours(millis);
    millis -= TimeUnit.HOURS.toMillis(hours);
    long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
    millis -= TimeUnit.MINUTES.toMillis(minutes);
    long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

    return (hours + " Hours " + minutes + " Minutes " + seconds + " Seconds");
  }

  public String convertMinutesToTimeString(int pastMinutes) {
    long days = TimeUnit.MINUTES.toDays(pastMinutes);
    long years = days / 365;
    long months = (days % 365) / 30;
    long weeks = (days % 30) / 7;
    days = days % 7;
    long hours = TimeUnit.MINUTES.toHours(pastMinutes) % 24;
    long minutes = pastMinutes % 60;

    StringBuilder result = new StringBuilder();

    if (years > 0) result.append(years).append(" year").append(years == 1 ? "" : "s").append(" ");
    if (months > 0)
      result.append(months).append(" month").append(months == 1 ? "" : "s").append(" ");
    if (weeks > 0) result.append(weeks).append(" week").append(weeks == 1 ? "" : "s").append(" ");
    if (days > 0) result.append(days).append(" day").append(days == 1 ? "" : "s").append(" ");
    if (hours > 0) result.append(hours).append(" hour").append(hours == 1 ? "" : "s").append(" ");
    if (minutes > 0)
      result.append(minutes).append(" minute").append(minutes == 1 ? "" : "s").append(" ");

    return result.toString();
  }

  /**
   * Unregisters a Bukkit command using reflection.
   *
   * @param command The command to unregister.
   * @param aparecium The plugin instance.
   * @throws Exception If reflection fails.
   */
  @SneakyThrows
  public void unRegisterBukkitCommand(PluginCommand command, Aparecium aparecium) {
    Object result =
        ReflectionUtility.getPrivateField(aparecium.getServer().getPluginManager(), "commandMap");
    SimpleCommandMap commandMap = (SimpleCommandMap) result;
    Object map = ReflectionUtility.getPrivateField(commandMap, "knownCommands");
    HashMap<String, Command> knownCommands = (HashMap<String, Command>) map;

    knownCommands.remove(command.getName());

    for (String alias : command.getAliases()) {
      if (knownCommands.containsKey(alias)
          && knownCommands.get(alias).toString().contains(aparecium.getName())) {
        knownCommands.remove(alias);
      }
    }
  }
}
