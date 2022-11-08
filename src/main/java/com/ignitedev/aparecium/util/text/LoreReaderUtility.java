/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.util.text;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public record LoreReaderUtility(List<Component> lore) {

  public LoreReaderUtility(@NotNull List<Component> lore) {
    this.lore = TextUtility.removeColorComponent(lore);
  }

  public List<String> getAsStringList() {
    return TextUtility.serializeComponent(this.lore);
  }

  public Component get(int index) {
    return lore.size() > index ? lore.get(index) : null;
  }

  public List<String> getMatching(@NotNull Predicate<String> predicate) {
    return getAsStringList().parallelStream().filter(predicate).collect(Collectors.toList());
  }

  public List<String> getAllValues(@NotNull String key) {
    Predicate<String> containsKey = line -> line.startsWith(key);
    Function<String, String> getValue = line -> line.replace(key, "");
    return getAsStringList().parallelStream().filter(containsKey).map(getValue)
        .collect(Collectors.toList());
  }

  public String getFirstValue(@NotNull String key) {
    Predicate<String> containsKey = line -> line.startsWith(key);
    Function<String, String> getValue = line -> line.replace(key, "");
    return getAsStringList().parallelStream().filter(containsKey).map(getValue).findFirst()
        .orElse(null);
  }

  public double getDouble(@NotNull String key) {
    List<String> values = getAllValues(key);
    String value = values.get(0);
    return Double.parseDouble(value);
  }

  public double getInt(@NotNull String key) {
    List<String> values = getAllValues(key);
    String value = values.get(0);
    return Integer.parseInt(value);
  }
}
