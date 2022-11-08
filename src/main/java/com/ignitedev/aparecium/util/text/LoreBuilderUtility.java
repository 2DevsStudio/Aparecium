/*
 * Copyright (c) 2022.  Made by 2DevsStudio LLC ( https://2devsstudio.com/ ) using one of our available slaves; IgniteDEV
 */

package com.ignitedev.aparecium.util.text;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import lombok.EqualsAndHashCode;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
@EqualsAndHashCode
public class LoreBuilderUtility {

  private List<Component> lore = new ArrayList<>();

  public LoreBuilderUtility() {}

  public LoreBuilderUtility(List<Component> list) {
    this.lore = list;
  }

  public LoreBuilderUtility(@NotNull LoreBuilderUtility other) {
    this.lore.addAll(other.lore);
  }

  @NotNull
  public LoreBuilderUtility append(@NotNull Component line) {
    lore.add(line);
    return this;
  }

  @NotNull
  public <T> LoreBuilderUtility appendIf(
      @NotNull Component line, @NotNull T predicateSource, @NotNull Predicate<T> predicate) {
    if (predicate.test(predicateSource)) {
      lore.add(line);
    }
    return this;
  }

  @NotNull
  public LoreBuilderUtility insert(int index, @NotNull Component line) {
    lore.add(index, line);
    return this;
  }

  @NotNull
  public <T> LoreBuilderUtility insertIf(
      int index,
      @NotNull Component line,
      @NotNull T predicateSource,
      @NotNull Predicate<T> predicate) {
    if (predicate.test(predicateSource)) {
      lore.add(index, line);
    }
    return this;
  }

  @NotNull
  public LoreBuilderUtility remove(int index) {
    lore.remove(index);
    return this;
  }

  @NotNull
  public LoreBuilderUtility remove(@NotNull Component line) {
    lore.remove(line);
    return this;
  }

  @NotNull
  public <T> LoreBuilderUtility removeIf(
      int index, @NotNull T predicateSource, @NotNull Predicate<T> predicate) {
    if (predicate.test(predicateSource)) {
      lore.remove(index);
    }
    return this;
  }

  @NotNull
  public <T> LoreBuilderUtility removeIf(
      @NotNull Component line, @NotNull T predicateSource, @NotNull Predicate<T> predicate) {
    if (predicate.test(predicateSource)) {
      lore.remove(line);
    }
    return this;
  }

  public LoreBuilderUtility removeLast() {
    int lastIndex = lore.size() - 1;

    if (lastIndex >= 0) {
      lore.remove(lastIndex);
    }
    return this;
  }

  public LoreBuilderUtility replace(@NotNull Component toReplace, @NotNull Component newValue) {
    lore.replaceAll((val) -> val.equals(toReplace) ? newValue : val);
    return this;
  }

  public LoreBuilderUtility replaceIf(
      @NotNull Predicate<String> predicate, @NotNull Component newValue) {
    lore.replaceAll((val) -> predicate.test(TextUtility.serializeComponent(val)) ? newValue : val);
    return this;
  }

  @NotNull
  public LoreBuilderUtility clear() {
    lore.clear();
    return this;
  }

  @NotNull
  public <T> LoreBuilderUtility clearIf(
      @NotNull T predicateSource, @NotNull Predicate<T> predicate) {
    if (predicate.test(predicateSource)) {
      lore.clear();
    }
    return this;
  }

  @NotNull
  public Component get(int index) {
    return lore.get(index);
  }

  public boolean isEmpty() {
    return lore.isEmpty();
  }

  public boolean contains(@NotNull Component line) {
    return lore.contains(line);
  }

  public List<Component> build() {
    return lore;
  }
}
